// src/main/java/com/example/demo/service/ComplianceRuleServiceImpl.java
package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.ComplianceRule;
import com.example.demo.repository.ComplianceRuleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ComplianceRuleServiceImpl implements ComplianceRuleService {
    
    private final ComplianceRuleRepository complianceRuleRepository;
    
    public ComplianceRuleServiceImpl(ComplianceRuleRepository complianceRuleRepository) {
        this.complianceRuleRepository = complianceRuleRepository;
    }
    
    @Override
    public ComplianceRule createRule(ComplianceRule rule) {
        // Check for duplicate rule name
        if (rule.getRuleName() != null && 
            complianceRuleRepository.findAll().stream()
                .anyMatch(r -> r.getRuleName().equals(rule.getRuleName()))) {
            throw new ValidationException("Rule name already exists: " + rule.getRuleName());
        }
        
        // Validate threshold
        if (rule.getThreshold() != null && rule.getThreshold() < 0) {
            throw new ValidationException("Threshold cannot be negative");
        }
        
        return complianceRuleRepository.save(rule);
    }
    
    @Override
    public List<ComplianceRule> getAllRules() {
        return complianceRuleRepository.findAll();
    }
    
    @Override
    public ComplianceRule getRule(Long id) {
        return complianceRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance rule not found with id: " + id));
    }
}