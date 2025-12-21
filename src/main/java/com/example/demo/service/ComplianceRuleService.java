package com.example.demo.service;

import com.example.demo.model.ComplianceRule;
import java.util.List;

public interface ComplianceRuleService {
    ComplianceRule createRule(ComplianceRule rule);
    ComplianceRule getRule(Long id);
    ComplianceRule getRuleById(Long id); // Add this
    List<ComplianceRule> getAllRules();
    ComplianceRule updateRule(ComplianceRule rule); // Add this
    void deleteRule(Long id);
}