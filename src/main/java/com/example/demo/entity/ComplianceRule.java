package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "ruleName"))
public class ComplianceRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruleName;
    private String ruleDescription;
    private String matchType;
    private Double threshold;

    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getRuleName() { return ruleName; }
    public String getMatchType() { return matchType; }
    public Double getThreshold() { return threshold; }

    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    public void setRuleDescription(String ruleDescription) { this.ruleDescription = ruleDescription; }
    public void setMatchType(String matchType) { this.matchType = matchType; }
    public void setThreshold(Double threshold) { this.threshold = threshold; }
}