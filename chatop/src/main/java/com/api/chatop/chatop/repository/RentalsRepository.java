package com.api.chatop.chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.chatop.chatop.model.Rentals;


@Repository
public interface RentalsRepository extends CrudRepository<Rentals, Long>{

}
