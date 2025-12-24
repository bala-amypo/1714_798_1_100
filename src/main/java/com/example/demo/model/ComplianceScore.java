package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "compliance_scores")
public class ComplianceScore {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false, unique = true)
    private Vendor vendor;
    
    @Column(nullable = false)
    private Double scoreValue;
    
    @Column(nullable = false)
    private LocalDateTime lastEvaluated;
    
    @Column(nullable = false)
    private String rating; // EXCELLENT, GOOD, POOR, NONCOMPLIANT
    
    public ComplianceScore() {}
    
    public ComplianceScore(Vendor vendor, Double scoreValue, String rating) {
        this.vendor = vendor;
        this.scoreValue = scoreValue;
        this.rating = rating;
    }
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastEvaluated = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Vendor getVendor() {
        return vendor;
    }
    
    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
    
    public Double getScoreValue() {
        return scoreValue;
    }
    
    public void setScoreValue(Double scoreValue) {
        this.scoreValue = scoreValue;
    }
    
    public LocalDateTime getLastEvaluated() {
        return lastEvaluated;
    }
    
    public void setLastEvaluated(LocalDateTime lastEvaluated) {
        this.lastEvaluated = lastEvaluated;
    }
    
    public String getRating() {
        return rating;
    }
    
    public void setRating(String rating) {
        this.rating = rating;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplianceScore that = (ComplianceScore) o;
        return Objects.equals(id, that.id) && Objects.equals(vendor, that.vendor);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, vendor);
    }
}