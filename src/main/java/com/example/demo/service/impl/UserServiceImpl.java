// com.example.demo.service.impl.UserServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException("Email already in use");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new com.example.demo.exception.ResourceNotFoundException("User not found"));
    }
}