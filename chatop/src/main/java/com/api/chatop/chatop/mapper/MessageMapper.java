package com.api.chatop.chatop.mapper;

import org.springframework.stereotype.Component;

import com.api.chatop.chatop.dto.request.CreateMessageRequestDTO;
import com.api.chatop.chatop.dto.response.MessageResponseDTO;
import com.api.chatop.chatop.model.Messages;

@Component
public class MessageMapper {

    public Messages toEntity(CreateMessageRequestDTO dto) {
        Messages message = new Messages();
        message.setRental_id(dto.getRentalId());
        message.setUser_id(dto.getUserId());
        message.setMessage_content(dto.getMessage());
        return message;
    }

    public MessageResponseDTO toResponseDTO(Messages message) {
        MessageResponseDTO dto = new MessageResponseDTO();
        dto.setId(message.getId());
        dto.setRentalId(message.getRental_id());
        dto.setUserId(message.getUser_id());
        dto.setMessage(message.getMessage_content());
        dto.setCreatedAt(message.getCreated_at());
        dto.setUpdatedAt(message.getUpdated_at());
        return dto;
    }
}
