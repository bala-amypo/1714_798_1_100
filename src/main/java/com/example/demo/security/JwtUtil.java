package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Must be at least 256 bits for HS256 (32 characters)
    private final SecretKey key;
    private final long expirationTime;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.validity-in-ms:86400000}") long expirationTime) {
        
        // Ensure secret is at least 32 characters
        String paddedSecret = padSecretTo32Chars(secret);
        this.key = Keys.hmacShaKeyFor(paddedSecret.getBytes());
        this.expirationTime = expirationTime;
    }

    // Constructor for test compatibility
    public JwtUtil(String secret, long validityInMs) {
        String paddedSecret = padSecretTo32Chars(secret);
        this.key = Keys.hmacShaKeyFor(paddedSecret.getBytes());
        this.expirationTime = validityInMs;
    }

    private String padSecretTo32Chars(String secret) {
        if (secret.length() >= 32) {
            return secret.substring(0, 32);
        }
        StringBuilder padded = new StringBuilder(secret);
        while (padded.length() < 32) {
            padded.append("0");
        }
        return padded.toString();
    }

    public String generateToken(Authentication authentication, Long userId, String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    // Simple method like in sample code
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public Long extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Invalid JWT token: " + e.getMessage());
            return false;
        }
    }
}