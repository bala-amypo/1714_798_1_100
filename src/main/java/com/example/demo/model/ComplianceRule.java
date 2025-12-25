package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String ruleName;
    
    private String ruleDescription;
    private String matchType;
    
    private Double threshold = 0.0;
    
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (threshold == null) {
            threshold = 0.0;
        }
    }
}