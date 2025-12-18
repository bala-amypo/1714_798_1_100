package com.example.demo.service;

import com.example.demo.model.ComplianceRule;
import java.util.List;
import java.util.Optional;

public interface ComplianceRuleService {

    // Used by controllers/tests
    ComplianceRule save(ComplianceRule rule);

    ComplianceRule createRule(ComplianceRule rule);

    // REQUIRED to fix "cannot find symbol findAll()"
    List<ComplianceRule> findAll();

    List<ComplianceRule> getAllRules();

    ComplianceRule getRule(Long id);
    
    // Added to fix compilation error in controller
    Optional<ComplianceRule> findById(Long id);
}