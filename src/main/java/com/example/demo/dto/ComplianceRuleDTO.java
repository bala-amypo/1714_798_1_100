package com.example.demo.dto;

public class ComplianceRuleDTO {
    private Long id;
    private String ruleName;
    private String ruleDescription;
    private String matchType; // EXPIRY_CHECK, DOCUMENT_REQUIRED, WEIGHTED_SCORE
    private Double threshold;
    
    // Constructors
    public ComplianceRuleDTO() {}
    
    public ComplianceRuleDTO(Long id, String ruleName, String ruleDescription, String matchType, Double threshold) {
        this.id = id;
        this.ruleName = ruleName;
        this.ruleDescription = ruleDescription;
        this.matchType = matchType;
        this.threshold = threshold;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRuleName() {
        return ruleName;
    }
    
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
    
    public String getRuleDescription() {
        return ruleDescription;
    }
    
    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }
    
    public String getMatchType() {
        return matchType;
    }
    
    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }
    
    public Double getThreshold() {
        return threshold;
    }
    
    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }
}