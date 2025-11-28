package com.api.chatop.chatop.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.api.chatop.chatop.dto.request.RegisterRequestDTO;
import com.api.chatop.chatop.dto.response.UserResponseDTO;
import com.api.chatop.chatop.model.Users;

@Component
public class UserMapper {
    
    private final ModelMapper modelMapper;
    
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public Users toEntity(RegisterRequestDTO dto) {
        return modelMapper.map(dto, Users.class);
    }
    
    public UserResponseDTO toResponseDTO(Users user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreated_at());
        return dto;
    }
}