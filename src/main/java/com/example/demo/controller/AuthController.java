package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try {
            log.info("Login attempt for email: {}", authRequest.getEmail());
            
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(), 
                    authRequest.getPassword()
                )
            );
            
            // Set authentication in context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Get user details
            User user = userService.findByEmail(authRequest.getEmail());
            log.info("User authenticated: {} with role: {}", user.getEmail(), user.getRole());
            
            // Generate token
            String token = jwtUtil.generateToken(authentication, user.getId(), user.getEmail(), user.getRole());
            
            // Return response
            return ResponseEntity.ok(new AuthResponse(
                token, 
                user.getId(), 
                user.getEmail(), 
                user.getRole()
            ));
            
        } catch (Exception e) {
            log.error("Login error for email {}: {}", authRequest.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(401).body(new AuthResponse(
                null, 
                null, 
                null, 
                "Authentication failed: Invalid email or password"
            ));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        try {
            log.info("Registering user with email: {}", user.getEmail());
            User registeredUser = userService.registerUser(user);
            log.info("User registered successfully: {}", registeredUser.getEmail());
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            log.error("Registration error: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth endpoint is working!");
    }
}