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

    private final SecretKey key;
    private final long expirationTime;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.validity-in-ms:86400000}") long expirationTime) {
        
        String paddedSecret = padSecretTo32Chars(secret);
        this.key = Keys.hmacShaKeyFor(paddedSecret.getBytes());
        this.expirationTime = expirationTime;
    }

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

    // Generate token with email as subject
    public String generateToken(Authentication authentication, Long userId, String email, String role) {
        return Jwts.builder()
                .setSubject(email) // Email as subject
                .claim("userId", userId)
                .claim("email", email) // Also store in claims
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    // Simple method for testing
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email) // Email as subject
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract email from token (from subject)
    public String extractEmail(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject(); // Get subject which should be email
        } catch (Exception e) {
            System.err.println("Error extracting email from token: " + e.getMessage());
            return null;
        }
    }

    // Alternative: Extract email from claims
    public String extractEmailFromClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("email", String.class);
        } catch (Exception e) {
            System.err.println("Error extracting email from claims: " + e.getMessage());
            return null;
        }
    }

    public String extractRole(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("role", String.class);
        } catch (Exception e) {
            System.err.println("Error extracting role from token: " + e.getMessage());
            return null;
        }
    }

    public Long extractUserId(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("userId", Long.class);
        } catch (Exception e) {
            System.err.println("Error extracting userId from token: " + e.getMessage());
            return null;
        }
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