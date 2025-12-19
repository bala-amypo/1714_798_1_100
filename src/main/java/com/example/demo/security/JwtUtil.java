// src/main/java/com/example/demo/security/JwtUtil.java
package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
    
    private final SecretKey secretKey;
    private final long validityInMs;
    
    public JwtUtil(@Value("${jwt.secret:defaultSecretKeyThatIsAtLeast32CharactersLong12345}") String secret,
                   @Value("${jwt.validity:3600000}") long validityInMs) {
        // Ensure the secret is at least 256 bits (32 bytes/characters)
        String safeSecret = ensureValidSecret(secret);
        this.secretKey = Keys.hmacShaKeyFor(safeSecret.getBytes(StandardCharsets.UTF_8));
        this.validityInMs = validityInMs;
    }
    
    private String ensureValidSecret(String secret) {
        if (secret == null || secret.length() < 32) {
            // Generate a safe default or pad the existing one
            String defaultSecret = "defaultSecretKeyThatIsAtLeast32CharactersLong12345";
            if (secret != null && !secret.isEmpty()) {
                // Pad the existing secret to meet requirements
                StringBuilder padded = new StringBuilder(secret);
                while (padded.length() < 32) {
                    padded.append("0");
                }
                return padded.toString().substring(0, 32);
            }
            return defaultSecret;
        }
        return secret;
    }
    
    public String generateToken(Authentication authentication, Long userId, String email, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validityInMs);
        
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
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
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Long.class);
    }
    
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    
    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }
    
    public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        String role = getRoleFromToken(token);
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }
}