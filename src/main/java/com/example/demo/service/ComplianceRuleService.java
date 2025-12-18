package com.example.demo.service;

import com.example.demo.model.ComplianceRule;
import java.util.List;

public interface ComplianceRuleService {

    // REQUIRED to fix "cannot find symbol save(...)"
    ComplianceRule save(ComplianceRule rule);

    ComplianceRule createRule(ComplianceRule rule);

    List<ComplianceRule> getAllRules();

    ComplianceRule getRule(Long id);
}
