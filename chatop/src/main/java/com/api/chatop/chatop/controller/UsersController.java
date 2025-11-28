package com.api.chatop.chatop.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.chatop.chatop.dto.request.RegisterRequestDTO;
import com.api.chatop.chatop.dto.response.UserResponseDTO;
import com.api.chatop.chatop.mapper.UserMapper;
import com.api.chatop.chatop.model.Users;
import com.api.chatop.chatop.service.UsersService;

import jakarta.validation.Valid;

@RestController
public class UsersController {

    private final UsersService usersService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UsersController(UsersService usersService, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("api/user")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody RegisterRequestDTO requestDTO) {
        Users user = userMapper.toEntity(requestDTO);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setCreated_at(LocalDateTime.now());

        Users savedUser = usersService.saveUser(user);
        UserResponseDTO response = userMapper.toResponseDTO(savedUser);

        return ResponseEntity.ok(response);
    }

    @GetMapping("api/user/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable final Long id) {
        Users user = usersService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDTO response = userMapper.toResponseDTO(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("api/users")
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        Iterable<Users> users = usersService.getAllUsers();

        List<UserResponseDTO> response = StreamSupport.stream(users.spliterator(), false)
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("api/user/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable final Long id,
                                                      @Valid @RequestBody RegisterRequestDTO requestDTO) {
        Users existingUser = usersService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(requestDTO.getName());
        existingUser.setEmail(requestDTO.getEmail());
        if (requestDTO.getPassword() != null && !requestDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        }
        existingUser.setUpdated_at(LocalDateTime.now());

        Users updatedUser = usersService.saveUser(existingUser);
        UserResponseDTO response = userMapper.toResponseDTO(updatedUser);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("api/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable final Long id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
