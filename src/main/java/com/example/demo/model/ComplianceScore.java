package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_scores")
public class ComplianceScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "vendor_id", unique = true, nullable = false)
    private Vendor vendor;
    
    @Column(nullable = false)
    private Double scoreValue = 0.0;
    
    @Column(nullable = false)
    private LocalDateTime lastEvaluated;
    
    @Column(nullable = false)
    private String rating = "NONCOMPLIANT";
    
    public ComplianceScore() {
    }
    
    public ComplianceScore(Vendor vendor, Double scoreValue) {
        this.vendor = vendor;
        this.scoreValue = scoreValue;
    }
    
    public ComplianceScore(Vendor vendor, Double scoreValue, String rating) {
        this.vendor = vendor;
        this.scoreValue = scoreValue;
        this.rating = rating;
    }
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastEvaluated = LocalDateTime.now();
        if (scoreValue < 0) {
            scoreValue = 0.0;
        }
        if (scoreValue > 100) {
            scoreValue = 100.0;
        }
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Vendor getVendor() { return vendor; }
    public void setVendor(Vendor vendor) { this.vendor = vendor; }
    public Double getScoreValue() { return scoreValue; }
    public void setScoreValue(Double scoreValue) { 
        this.scoreValue = scoreValue;
        if (this.scoreValue < 0) {
            this.scoreValue = 0.0;
        }
        if (this.scoreValue > 100) {
            this.scoreValue = 100.0;
        }
    }
    public LocalDateTime getLastEvaluated() { return lastEvaluated; }
    public void setLastEvaluated(LocalDateTime lastEvaluated) { this.lastEvaluated = lastEvaluated; }
    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }
}