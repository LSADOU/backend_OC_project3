package com.api.chatop.chatop.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime updatedAt;
}