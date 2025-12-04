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
public class RentalResponseDTO {
    private Long id;
    private String name;
    private Integer surface;
    private Integer price;
    private String picture;
    private String description;

    @JsonProperty("owner_id")
    private Long ownerId;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime updatedAt;
}