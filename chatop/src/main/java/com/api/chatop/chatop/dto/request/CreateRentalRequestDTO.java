package com.api.chatop.chatop.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRentalRequestDTO {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Surface is required")
    @Min(value = 0, message = "Surface must be positive")
    private Integer surface;
    
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be positive")
    private Integer price;
    
    @NotNull(message = "Picture is required")
    private MultipartFile picture;
    
    @NotBlank(message = "Description is required")
    private String description;
    
}