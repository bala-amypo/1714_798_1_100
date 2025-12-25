package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String ruleName;
    
    private String ruleDescription;
    private String matchType;
    
    private Double threshold = 0.0;
    
    private LocalDateTime createdAt;
    
    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (threshold == null) {
            threshold = 0.0;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplianceRule)) return false;
        return id != null && id.equals(((ComplianceRule) o).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return "ComplianceRule{" +
                "id=" + id +
                ", ruleName='" + ruleName + '\'' +
                ", threshold=" + threshold +
                '}';
    }
}