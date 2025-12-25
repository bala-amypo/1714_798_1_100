package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    
    private final SecretKey secretKey;
    private final long validityInMilliseconds;
    
    // Constructor for Spring - using properties
    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.validity-in-ms}") long validityInMs) {
        
        // Ensure the secret is at least 256 bits (32 characters)
        if (secret.length() < 32) {
            // Pad the secret if it's too short
            secret = padSecret(secret);
        }
        
        // Convert string to proper key
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.validityInMilliseconds = validityInMs;
    }
    
    // Constructor for test compatibility
    public JwtUtil(String secret, long validityInMs) {
        if (secret.length() < 32) {
            secret = padSecret(secret);
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.validityInMilliseconds = validityInMs;
    }
    
    private String padSecret(String secret) {
        // Pad with characters to reach 32 characters
        StringBuilder padded = new StringBuilder(secret);
        while (padded.length() < 32) {
            padded.append("0");
        }
        // If still too short, repeat
        while (padded.length() < 32) {
            padded.append(padded.toString());
        }
        return padded.substring(0, 32);
    }
    
    public String generateToken(Authentication authentication, Long userId, String email, String role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.err.println("JWT validation error: " + e.getMessage());
            return false;
        }
    }
    
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("userId", Long.class);
    }
    
    public String getRoleFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("role", String.class);
    }
    
    public String getEmailFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("email", String.class);
    }
    
    public String getSubjectFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }
    
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}