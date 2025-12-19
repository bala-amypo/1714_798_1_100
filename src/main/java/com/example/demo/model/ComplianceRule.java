package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_rules", uniqueConstraints = {
    @UniqueConstraint(columnNames = "ruleName")
})
public class ComplianceRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(name = "rule_name", unique = true)
    private String ruleName;
    
    @Column(name = "rule_description")
    private String ruleDescription;
    
    @Column(name = "match_type")
    private String matchType;
    
    @Min(0)
    private Double threshold = 0.0;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (threshold == null) threshold = 0.0;
    }
    
    public ComplianceRule() {}
    
    public ComplianceRule(String ruleName, String ruleDescription, String matchType, Double threshold) {
        this.ruleName = ruleName;
        this.ruleDescription = ruleDescription;
        this.matchType = matchType;
        this.threshold = threshold;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    
    public String getRuleDescription() { return ruleDescription; }
    public void setRuleDescription(String ruleDescription) { this.ruleDescription = ruleDescription; }
    
    public String getMatchType() { return matchType; }
    public void setMatchType(String matchType) { this.matchType = matchType; }
    
    public Double getThreshold() { return threshold; }
    public void setThreshold(Double threshold) { this.threshold = threshold; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}