package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class ComplianceRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruleName;
    private String description;
    private Integer weight;

    private String name;        
    private String status;      
    private String ruleType;    

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

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getRuleType() {
        return ruleType;
    }


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
