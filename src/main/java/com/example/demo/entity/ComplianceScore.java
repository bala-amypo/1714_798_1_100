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

    public Long getId() { return id; }
    public Vendor getVendor() { return vendor; }
    public Double getScoreValue() { return scoreValue; }
    public String getRating() { return rating; }

    public void setVendor(Vendor vendor) { this.vendor = vendor; }
    public void setScoreValue(Double scoreValue) { this.scoreValue = scoreValue; }
    public void setRating(String rating) { this.rating = rating; }
    public void setLastEvaluated(LocalDateTime lastEvaluated) { this.lastEvaluated = lastEvaluated; }
}