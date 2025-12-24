package com.example.demo.service.impl;

import com.example.demo.service.JwtService;
import com.example.demo.util.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {
    
    private final JwtUtil jwtUtil;
    
    public JwtServiceImpl(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    
    @Override
    public String extractUsername(String token) {
        return jwtUtil.extractUsername(token);
    }
    
    @Override
    public String generateToken(UserDetails userDetails) {
        return jwtUtil.generateToken(userDetails);
    }
    
    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        return jwtUtil.validateToken(token, userDetails);
    }
}