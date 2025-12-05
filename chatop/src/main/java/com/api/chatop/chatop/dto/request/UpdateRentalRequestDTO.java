package com.api.chatop.chatop.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateRentalRequestDTO {
    private String name;
    
    @Min(value = 1, message = "Surface must be at least 1")
    private Integer surface;
    
    @Min(value = 0, message = "Price must be positive")
    private Integer price;
    
    private String description;
}