package com.api.chatop.chatop.security.service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.chatop.chatop.model.Users;
import com.api.chatop.chatop.repository.UsersRepository;
import com.api.chatop.chatop.security.model.UserSecurity;

@Service
public class UserSecurityService implements UserDetailsService{

    private final UsersRepository userRepository;
    
    public UserSecurityService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new UserSecurity(user);
    }
    
}
