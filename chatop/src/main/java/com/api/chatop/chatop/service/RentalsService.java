package com.api.chatop.chatop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.chatop.chatop.repository.RentalsRepository;
import com.api.chatop.chatop.model.Rentals;
import lombok.Data;

@Data
@Service
public class RentalsService {

    @Autowired
    private RentalsRepository RentalsRepository;

    public Optional<Rentals> getRentalById(final Long id) {
        return RentalsRepository.findById(id);
    }

    public Iterable<Rentals> getAllRentals() {
        return RentalsRepository.findAll();
    }

    public void deleteRental(final Long id) {
        RentalsRepository.deleteById(id);
    }

    public Rentals saveRental(Rentals Rental) {
        return RentalsRepository.save(Rental);
    }
}