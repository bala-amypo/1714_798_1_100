package com.example.demo.service;

import com.example.demo.model.ComplianceRule;
import java.util.List;
import java.util.Optional;

public interface ComplianceRuleService {
    ComplianceRule createRule(ComplianceRule rule);
    ComplianceRule getRule(Long id);
    List<ComplianceRule> getAllRules();
    void deleteRule(Long id);
}