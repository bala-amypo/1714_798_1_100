package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final UserService userService;
    
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Check if email already exists
            User existingUser = userService.findByEmail(user.getEmail());
            if (existingUser != null) {
                response.put("success", false);
                response.put("message", "Email already exists");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Set default role if not provided
            if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("USER");
            }
            
            // Save the user
            User registeredUser = userService.registerUser(user);
            
            // Return success response without password
            registeredUser.setPassword(null);
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("user", registeredUser);
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Registration failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User loginRequest) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();
            
            if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
                response.put("success", false);
                response.put("message", "Email and password are required");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Find user by email
            User user = userService.findByEmail(email);
            
            if (user == null) {
                response.put("success", false);
                response.put("message", "Invalid email or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            // In a real application, you would hash the password and compare
            // For now, doing basic comparison
            if (user.getPassword().equals(password)) {
                // Return user data (without password for security)
                user.setPassword(null);
                response.put("success", true);
                response.put("message", "Login successful");
                response.put("user", user);
                
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Invalid email or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/check-email/{email}")
    public ResponseEntity<Map<String, Object>> checkEmailExists(@PathVariable String email) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            User user = userService.findByEmail(email);
            response.put("exists", user != null);
            response.put("message", user != null ? "Email already exists" : "Email available");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("exists", false);
            response.put("message", "Error checking email");
            return ResponseEntity.ok(response);
        }
    }
}