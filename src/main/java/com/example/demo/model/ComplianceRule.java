package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class ComplianceRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Existing fields
    private String ruleName;
    private String description;
    private Integer weight;

    // ✅ Newly added fields
    private String name;        // Rule display name
    private String status;      // ACTIVE / INACTIVE
    private String ruleType;    // MANDATORY / OPTIONAL / CUSTOM

    // -------- Getters --------

    public Long getId() {
        return id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public String getDescription() {
        return description;
    }

    public Integer getWeight() {
        return weight;
    }

    // ✅ Newly added getters
    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getRuleType() {
        return ruleType;
    }

    // -------- Setters --------

    public void setId(Long id) {
        this.id = id;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    // ✅ Newly added setters
    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }
}
