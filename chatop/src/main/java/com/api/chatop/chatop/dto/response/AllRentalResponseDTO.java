package com.api.chatop.chatop.dto.response;
import lombok.Data;

import java.util.List;

import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AllRentalResponseDTO {
    
    private List <RentalResponseDTO> rentals;

    public AllRentalResponseDTO(List<RentalResponseDTO> rentals) {
        this.rentals = rentals;
    }
}
