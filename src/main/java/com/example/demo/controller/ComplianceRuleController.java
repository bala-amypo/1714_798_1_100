package com.example.demo.service;

import com.example.demo.model.ComplianceRule;
import java.util.List;
import java.util.Optional;

public interface ComplianceRuleService {

    ComplianceRule save(ComplianceRule rule);

    ComplianceRule createRule(ComplianceRule rule);

    List<ComplianceRule> findAll();

    List<ComplianceRule> getAllRules();

    ComplianceRule getRule(Long id);

    // REQUIRED method
    Optional<ComplianceRule> findById(Long id);
}
