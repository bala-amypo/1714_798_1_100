package com.example.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@Slf4j
public class RequestDebugFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        // Log ALL incoming requests
        log.info("=== REQUEST DEBUG ===");
        log.info("URL: {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());
        log.info("Authorization Header: {}", httpRequest.getHeader("Authorization"));
        log.info("Content-Type: {}", httpRequest.getHeader("Content-Type"));
        
        // Check if Authorization header exists and is valid
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null) {
            log.info("Auth Header Length: {}", authHeader.length());
            log.info("Auth Header starts with 'Bearer ': {}", authHeader.startsWith("Bearer "));
            
            if (authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                log.info("Token extracted: {}", token.substring(0, Math.min(20, token.length())) + "...");
                log.info("Token length: {}", token.length());
            }
        } else {
            log.warn("NO Authorization header found!");
        }
        
        log.info("=== END DEBUG ===");
        
        chain.doFilter(request, response);
    }
}