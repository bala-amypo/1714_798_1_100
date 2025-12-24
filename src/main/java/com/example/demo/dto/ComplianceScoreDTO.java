package com.example.demo.dto;

public class ComplianceScoreDTO {
    
    private Long id;
    private Long vendorId;
    private String vendorName;
    private Double scoreValue;
    private String rating;
    private String lastEvaluated;
    
    public ComplianceScoreDTO() {}
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getVendorId() {
        return vendorId;
    }
    
    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }
    
    public String getVendorName() {
        return vendorName;
    }
    
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    
    public Double getScoreValue() {
        return scoreValue;
    }
    
    public void setScoreValue(Double scoreValue) {
        this.scoreValue = scoreValue;
    }
    
    public String getRating() {
        return rating;
    }
    
    public void setRating(String rating) {
        this.rating = rating;
    }
    
    public String getLastEvaluated() {
        return lastEvaluated;
    }
    
    public void setLastEvaluated(String lastEvaluated) {
        this.lastEvaluated = lastEvaluated;
    }
}