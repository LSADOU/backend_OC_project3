package com.api.chatop.chatop.controller;

import java.util.Map;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.chatop.chatop.dto.request.LoginRequestDTO;
import com.api.chatop.chatop.dto.request.RegisterRequestDTO;
import com.api.chatop.chatop.dto.response.AuthResponseDTO;
import com.api.chatop.chatop.dto.response.UserResponseDTO;
import com.api.chatop.chatop.mapper.UserMapper;
import com.api.chatop.chatop.model.Users;
import com.api.chatop.chatop.security.service.JWTService;
import com.api.chatop.chatop.service.UsersService;

import jakarta.validation.Valid;

@RestController
public class AuthController {

    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final UserMapper userMapper;

    public AuthController(UsersService usersService, PasswordEncoder passwordEncoder, JWTService jwtService, UserMapper userMapper) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @PostMapping("api/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO requestDTO) {
        if (this.usersService.existsByEmail(requestDTO.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already in use"));
        }

        Users user = userMapper.toEntity(requestDTO);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setCreated_at(LocalDateTime.now());

        this.usersService.saveUser(user);
        String token = jwtService.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("api/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        Users existingUser = this.usersService.getUserByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("email not found"));

        if (passwordEncoder.matches(requestDTO.getPassword(), existingUser.getPassword())) {
            String token = jwtService.generateToken(requestDTO.getEmail());
            return ResponseEntity.ok(new AuthResponseDTO(token));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
        }
    }

    @GetMapping("api/auth/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String auth_email = authentication.getName();

        Users user = usersService.getUserByEmail(auth_email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDTO response = userMapper.toResponseDTO(user);

        return ResponseEntity.ok(response);
    }

}
