package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Allow all public endpoints
                .requestMatchers("/", "/health", "/error").permitAll()
                
                // Allow Swagger/OpenAPI endpoints
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/v3/api-docs",
                    "/api-docs/**",
                    "/api-docs",
                    "/webjars/**",
                    "/swagger-resources/**",
                    "/swagger-resources"
                ).permitAll()
                
                // Allow auth endpoints
                .requestMatchers("/auth/**").permitAll()
                
                // Allow H2 console (for testing)
                .requestMatchers("/h2-console/**").permitAll()
                
                // Secure API endpoints
                .requestMatchers("/api/**").authenticated()
                
                // Allow everything else (this will give whitelabel errors)
                .anyRequest().permitAll()
            )
            
            // Allow H2 console frames (for testing)
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        
        return http.build();
    }
}