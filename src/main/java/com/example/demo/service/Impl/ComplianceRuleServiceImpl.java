package com.example.demo.service.impl;

import com.example.demo.repository.ComplianceRuleRepository;
import org.springframework.stereotype.Service;

@Service
public class ComplianceRuleServiceImpl {
    
    private final ComplianceRuleRepository complianceRuleRepository;
    
    public ComplianceRuleServiceImpl(ComplianceRuleRepository complianceRuleRepository) {
        this.complianceRuleRepository = complianceRuleRepository;
    }
    
    // Add methods as needed
}