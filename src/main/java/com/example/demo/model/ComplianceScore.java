package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ComplianceScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Vendor vendor;

    private Double scoreValue;

    private String rating;

    private LocalDateTime lastEvaluated;

    // getters and setters
}
