package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_scores", uniqueConstraints = {
    @UniqueConstraint(columnNames = "vendor_id")
})
public class ComplianceScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "vendor_id", unique = true, nullable = false)
    private Vendor vendor;
    
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @Column(name = "score_value")
    private Double scoreValue = 0.0;
    
    @Column(name = "last_evaluated")
    private LocalDateTime lastEvaluated;
    
    private String rating;
    
    public ComplianceScore() {}
    
    public ComplianceScore(Vendor vendor, Double scoreValue) {
        this.vendor = vendor;
        this.scoreValue = scoreValue;
        this.lastEvaluated = LocalDateTime.now();
        this.rating = deriveRating(scoreValue);
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Vendor getVendor() { return vendor; }
    public void setVendor(Vendor vendor) { this.vendor = vendor; }
    
    public Double getScoreValue() { return scoreValue; }
    public void setScoreValue(Double scoreValue) { 
        this.scoreValue = scoreValue;
        this.rating = deriveRating(scoreValue);
    }
    
    public LocalDateTime getLastEvaluated() { return lastEvaluated; }
    public void setLastEvaluated(LocalDateTime lastEvaluated) { this.lastEvaluated = lastEvaluated; }
    
    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }
    
    private String deriveRating(Double score) {
        if (score == null) return "NONCOMPLIANT";
        if (score >= 90) return "EXCELLENT";
        if (score >= 70) return "GOOD";
        if (score >= 40) return "POOR";
        return "NONCOMPLIANT";
    }
}