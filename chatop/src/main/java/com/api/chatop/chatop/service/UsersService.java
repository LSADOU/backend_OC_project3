package com.api.chatop.chatop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.chatop.chatop.repository.UsersRepository;
import com.api.chatop.chatop.model.Users;
import lombok.Data;

@Data
@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public Optional<Users> getUserById(final Long id) {
        return usersRepository.findById(id);
    }

    public Optional<Users> getUserByEmail(final String email) {
        return usersRepository.findByEmail(email);
    }

    public Iterable<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public void deleteUser(final Long id) {
        usersRepository.deleteById(id);
    }

    public Users saveUser(Users u) {
        u.setUpdated_at(java.time.LocalDateTime.now());
        return usersRepository.save(u);
    }

    public boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }
}
