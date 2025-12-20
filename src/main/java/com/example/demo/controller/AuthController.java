package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        // Check if user already exists
        try {
            User existingUser = userService.findByEmail(registerRequest.getEmail());
            if (existingUser != null) {
                throw new IllegalArgumentException("Email already in use");
            }
        } catch (Exception e) {
            // User not found, that's good
        }
        
        // Create new user
        User user = new User();
        user.setFullName(registerRequest.getFullName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole("USER"); // Default role
        
        // Save user (make sure UserService.register method exists)
        User savedUser = userService.register(user);
        
        // Generate token WITHOUT authentication
        String token = jwtUtil.generateToken(
            savedUser.getId(),
            savedUser.getEmail(),
            savedUser.getRole()
        );
        
        return ResponseEntity.ok(new AuthResponse(token, savedUser.getId(), savedUser.getEmail(), savedUser.getRole()));
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        // Find user by email
        User user = userService.findByEmail(authRequest.getEmail());
        
        if (user == null || !passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        
        // Generate token
        String token = jwtUtil.generateToken(
            user.getId(),
            user.getEmail(),
            user.getRole()
        );
        
        return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getEmail(), user.getRole()));
    }
}