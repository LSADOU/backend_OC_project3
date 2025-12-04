package com.api.chatop.chatop.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMessageRequestDTO {
    @NotNull(message = "Rental ID is required")
    @JsonProperty("rentalId")
    @JsonAlias({"rental_id"})
    private Long rentalId;
    
    @NotNull(message = "User ID is required")
    @JsonProperty("userId")
    @JsonAlias({"user_id"})
    private Long userId;
    
    @NotBlank(message = "Message content is required")
    private String message;
}