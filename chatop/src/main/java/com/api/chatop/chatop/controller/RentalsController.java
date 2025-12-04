package com.api.chatop.chatop.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.chatop.chatop.dto.request.CreateRentalRequestDTO;
import com.api.chatop.chatop.dto.request.UpdateRentalRequestDTO;
import com.api.chatop.chatop.dto.response.AllRentalResponseDTO;
import com.api.chatop.chatop.dto.response.RentalResponseDTO;
import com.api.chatop.chatop.mapper.RentalMapper;
import com.api.chatop.chatop.model.Rentals;
import com.api.chatop.chatop.model.Users;
import com.api.chatop.chatop.service.FileStorageService;
import com.api.chatop.chatop.service.RentalsService;
import com.api.chatop.chatop.service.UsersService;
import org.springframework.beans.factory.annotation.Value;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Rentals", description = "Gestion des locations")
public class RentalsController {

    private final FileStorageService fileStorageService;
    private final RentalsService rentalsService;
    private final RentalMapper rentalMapper;
    private final UsersService usersService;
    @Value("${upload.path}")
    private String uploadPath;


    public RentalsController(RentalsService rentalsService, RentalMapper rentalMapper, FileStorageService fileStorageService, UsersService usersService) {
        this.fileStorageService = fileStorageService;
        this.rentalsService = rentalsService;
        this.rentalMapper = rentalMapper;
        this.usersService = usersService;
    }

    @Operation(summary = "Créer une location", description = "Crée une nouvelle location avec image")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Location créée"),
        @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    @PostMapping(value = "/api/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RentalResponseDTO> createRentals(@Valid @ModelAttribute CreateRentalRequestDTO requestDTO,
                                                           @AuthenticationPrincipal Jwt jwt) {
        
        Rentals rental = rentalMapper.toEntity(requestDTO);
        rental.setCreated_at(LocalDateTime.now());
        String email = jwt.getSubject();
        Users user = usersService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        rental.setOwner_id(user.getId());
        String filename = fileStorageService.storeFile(requestDTO.getPicture());     
        rental.setPicture(filename);                                              
        Rentals savedRental = rentalsService.saveRental(rental);
        RentalResponseDTO response = rentalMapper.toResponseDTO(savedRental);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtenir une location", description = "Retourne une location par son ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Location trouvée"),
        @ApiResponse(responseCode = "404", description = "Location non trouvée")
    })
    @GetMapping("/api/rentals/{id}")
    public ResponseEntity<RentalResponseDTO> getRental(@PathVariable("id") final Long id) {
        Rentals rental = rentalsService.getRentalById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        RentalResponseDTO response = rentalMapper.toResponseDTO(rental);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Lister les locations", description = "Retourne toutes les locations")
    @ApiResponse(responseCode = "200", description = "Liste des locations")
    @GetMapping("/api/rentals")
    public ResponseEntity<AllRentalResponseDTO> getRentals() {
        Iterable<Rentals> rentals = rentalsService.getAllRentals();

        List<RentalResponseDTO> response = StreamSupport.stream(rentals.spliterator(), false)
                .map(rentalMapper::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new AllRentalResponseDTO(response));
    }

    @Operation(summary = "Modifier une location", description = "Met à jour une location existante")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Location modifiée"),
        @ApiResponse(responseCode = "404", description = "Location non trouvée")
    })
    @PutMapping("/api/rentals/{id}")
    public ResponseEntity<RentalResponseDTO> updateRental(@PathVariable final Long id,
                                                          @Valid @RequestBody UpdateRentalRequestDTO requestDTO) {
        Rentals existingRental = rentalsService.getRentalById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        if (requestDTO.getName() != null) {
            existingRental.setName(requestDTO.getName());
        }
        if (requestDTO.getSurface() != null) {
            existingRental.setSurface(requestDTO.getSurface());
        }
        if (requestDTO.getPrice() != null) {
            existingRental.setPrice(requestDTO.getPrice());
        }
        if (requestDTO.getPicture() != null) {
            existingRental.setPicture(requestDTO.getPicture());
        }
        if (requestDTO.getDescription() != null) {
            existingRental.setDescription(requestDTO.getDescription());
        }
        existingRental.setUpdated_at(LocalDateTime.now());

        Rentals updatedRental = rentalsService.saveRental(existingRental);
        RentalResponseDTO response = rentalMapper.toResponseDTO(updatedRental);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Supprimer une location", description = "Supprime une location par son ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Location supprimée"),
        @ApiResponse(responseCode = "404", description = "Location non trouvée")
    })
    @DeleteMapping("api/rentals/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable("id") final Long id) {
        rentalsService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }
}
