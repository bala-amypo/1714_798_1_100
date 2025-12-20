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
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        // Create user from register request
        User user = new User();
        user.setFullName(registerRequest.getFullName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole("USER"); // Default role
        
        // Save user - this should throw ValidationException if email exists
        User registeredUser = userService.register(user);
        
        // Generate token directly WITHOUT authentication
        String token = jwtUtil.generateToken(
            registeredUser.getId(),
            registeredUser.getEmail(),
            registeredUser.getRole()
        );
        
        // Return response with token
        return ResponseEntity.ok(new AuthResponse(token, registeredUser.getId(), 
            registeredUser.getEmail(), registeredUser.getRole()));
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        // Find user by email
        User user = userService.findByEmail(authRequest.getEmail());
        
        // Verify password
        if (user == null || !passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        
        // Generate token
        String token = jwtUtil.generateToken(
            user.getId(),
            user.getEmail(),
            user.getRole()
        );
        
        // Return response with token
        return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getEmail(), user.getRole()));
    }
}