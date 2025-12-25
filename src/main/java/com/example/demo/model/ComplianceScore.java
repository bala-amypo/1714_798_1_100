package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_scores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "vendor_id", nullable = false, unique = true)
    private Vendor vendor;
    
    @Column(nullable = false)
    private Double scoreValue = 0.0;
    
    private LocalDateTime lastEvaluated;
    
    private String rating;
}