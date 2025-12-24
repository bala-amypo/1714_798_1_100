package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_scores")
public class ComplianceScore {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;
    
    @Column(name = "score_value", nullable = false)
    private Double scoreValue;
    
    @Column(nullable = false, length = 50)
    private String rating;
    
    @Column(name = "total_weight")
    private Integer totalWeight;
    
    @Column(name = "achieved_weight")
    private Integer achievedWeight;
    
    @Column(name = "total_required")
    private Integer totalRequired;
    
    @Column(name = "total_compliant")
    private Integer totalCompliant;
    
    @Column(name = "evaluated_at")
    private LocalDateTime evaluatedAt;
    
    @Column(name = "valid_until")
    private LocalDateTime validUntil;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Vendor getVendor() { return vendor; }
    public void setVendor(Vendor vendor) { this.vendor = vendor; }
    
    public Double getScoreValue() { return scoreValue; }
    public void setScoreValue(Double scoreValue) { this.scoreValue = scoreValue; }
    
    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }
    
    public Integer getTotalWeight() { return totalWeight; }
    public void setTotalWeight(Integer totalWeight) { this.totalWeight = totalWeight; }
    
    public Integer getAchievedWeight() { return achievedWeight; }
    public void setAchievedWeight(Integer achievedWeight) { this.achievedWeight = achievedWeight; }
    
    public Integer getTotalRequired() { return totalRequired; }
    public void setTotalRequired(Integer totalRequired) { this.totalRequired = totalRequired; }
    
    public Integer getTotalCompliant() { return totalCompliant; }
    public void setTotalCompliant(Integer totalCompliant) { this.totalCompliant = totalCompliant; }
    
    public LocalDateTime getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(LocalDateTime evaluatedAt) { this.evaluatedAt = evaluatedAt; }
    
    public LocalDateTime getValidUntil() { return validUntil; }
    public void setValidUntil(LocalDateTime validUntil) { this.validUntil = validUntil; }
}