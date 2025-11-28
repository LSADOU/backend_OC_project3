package com.api.chatop.chatop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMessageRequestDTO {
    @NotNull(message = "Rental ID is required")
    private Long rentalId;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "Message content is required")
    private String message;
}