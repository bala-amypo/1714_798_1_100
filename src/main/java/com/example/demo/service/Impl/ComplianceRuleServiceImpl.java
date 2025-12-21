package com.example.demo.service;

import com.example.demo.model.ComplianceRule;
import com.example.demo.repository.ComplianceRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ComplianceRuleServiceImpl implements ComplianceRuleService {
    
    private final ComplianceRuleRepository complianceRuleRepository;
    
    @Autowired
    public ComplianceRuleServiceImpl(ComplianceRuleRepository complianceRuleRepository) {
        this.complianceRuleRepository = complianceRuleRepository;
    }
    
    @Override
    public ComplianceRule createRule(ComplianceRule rule) {
        // Enforce uniqueness of ruleName if specified
        if (rule.getRuleName() != null && !rule.getRuleName().isEmpty()) {
            boolean exists = complianceRuleRepository.findAll().stream()
                .anyMatch(r -> rule.getRuleName().equals(r.getRuleName()));
            if (exists) {
                throw new RuntimeException("Duplicate rule name");
            }
        }
        
        // Ensure threshold is not negative
        if (rule.getThreshold() < 0) {
            rule.setThreshold(0.0);
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
            .orElseThrow(() -> new RuntimeException("Compliance rule not found"));
    }
}