package com.example.demo.service.impl;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor with EXACTLY these parameters in this order[citation:5][citation:9]
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) throws ValidationException {
        // 1. Check for duplicate email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ValidationException("Email already in use"); // Exact error message from STEP 0
        }

        // 2. Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // 3. Set default role if not provided
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        
        // 4. Set creation timestamp (can also be @PrePersist in entity)
        user.setCreatedAt(LocalDateTime.now());

        // 5. Save and return the user
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}