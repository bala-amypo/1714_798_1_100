package com.example.demo.service;

import com.example.demo.model.ComplianceRule;
import java.util.List;
import java.util.Optional;

public interface ComplianceRuleService {

    ComplianceRule save(ComplianceRule rule);

    List<ComplianceRule> findAll();

    Optional<ComplianceRule> findById(Long id);
}
