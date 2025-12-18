package com.example.demo.service;

import com.example.demo.model.ComplianceRule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplianceRuleService {

    public ComplianceRule save(ComplianceRule rule) {
        return rule;
    }

    public List<ComplianceRule> findAll() {
        return new ArrayList<>();
    }

    public ComplianceRule findById(Long id) {
        return new ComplianceRule();
    }
}
