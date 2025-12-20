package com.example.demo.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    
    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");
        System.out.println("DEBUG: JwtAuthenticationFilter - Authorization Header: " + authHeader); // ADD THIS
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("DEBUG: No Bearer token found or invalid header"); // ADD THIS
            filterChain.doFilter(request, response);
            return;
        }
        
        try {
            final String token = authHeader.substring(7); // Remove "Bearer "
            System.out.println("DEBUG: Token extracted: " + token.substring(0, Math.min(20, token.length())) + "..."); // ADD THIS
            
            final String email = jwtUtil.getEmailFromToken(token);
            System.out.println("DEBUG: Email from token: " + email); // ADD THIS
            
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
                
                if (jwtUtil.validateToken(token)) {
                    System.out.println("DEBUG: Token validated successfully"); // ADD THIS
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("DEBUG: SecurityContext set with user: " + email); // ADD THIS
                } else {
                    System.out.println("DEBUG: Token validation failed"); // ADD THIS
                }
            }
        } catch (Exception e) {
            System.out.println("DEBUG: Error processing token: " + e.getMessage()); // ADD THIS
            e.printStackTrace();
        }
        
        filterChain.doFilter(request, response);
    }
}