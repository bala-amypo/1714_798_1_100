package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Map<String, String> userData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String fullName = userData.get("fullName");
            String email = userData.get("email");
            String password = userData.get("password");
            String role = userData.get("role");
            
            // Validate required fields
            if (fullName == null || fullName.isEmpty() || 
                email == null || email.isEmpty() || 
                password == null || password.isEmpty()) {
                response.put("success", false);
                response.put("message", "fullName, email, and password are required");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Check if email already exists
            User existingUser = userService.findByEmail(email);
            if (existingUser != null) {
                response.put("success", false);
                response.put("message", "Email already exists");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Create new user
            User user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPassword(password); // Store plain text for now
            user.setRole(role != null ? role : "USER");
            user.setCreatedAt(LocalDateTime.now());
            
            // Save the user
            User registeredUser = userService.registerUser(user);
            
            // Remove password from response
            registeredUser.setPassword(null);
            
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("user", registeredUser);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Registration failed: " + e.getMessage());
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String email = loginData.get("email");
            String password = loginData.get("password");
            
            if (email == null || password == null) {
                response.put("success", false);
                response.put("message", "Email and password are required");
                return ResponseEntity.badRequest().body(response);
            }
            
            User user = userService.findByEmail(email);
            
            if (user == null) {
                response.put("success", false);
                response.put("message", "User not found");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            // Simple password check (for testing only)
            if (user.getPassword().equals(password)) {
                user.setPassword(null); // Remove password from response
                response.put("success", true);
                response.put("message", "Login successful");
                response.put("user", user);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Invalid password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // Simple health check
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth endpoint is working!");
    }
}