package com.api.chatop.chatop.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.api.chatop.chatop.dto.request.CreateRentalRequestDTO;
import com.api.chatop.chatop.dto.response.RentalResponseDTO;
import com.api.chatop.chatop.model.Rentals;

@Component
public class RentalMapper {

    @Value("${app.base-url}")
    private String baseUrl;
    
    public Rentals toEntity(CreateRentalRequestDTO dto) {
        Rentals rental = new Rentals();
        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setDescription(dto.getDescription());
        return rental;
    }
    
    public RentalResponseDTO toResponseDTO(Rentals rental) {
        RentalResponseDTO dto = new RentalResponseDTO();
        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());

        if (rental.getPicture() != null) {
            dto.setPicture(baseUrl + "/uploads/" + rental.getPicture());
        }

        dto.setDescription(rental.getDescription());
        dto.setOwnerId(rental.getOwner_id());
        dto.setCreatedAt(rental.getCreated_at());
        dto.setUpdatedAt(rental.getUpdated_at());
        return dto;
    }
}