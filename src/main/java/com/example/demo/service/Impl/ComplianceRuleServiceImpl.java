package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ComplianceRule;
import com.example.demo.repository.ComplianceRuleRepository;
import com.example.demo.service.ComplianceRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplianceRuleServiceImpl implements ComplianceRuleService {
    
    private final ComplianceRuleRepository complianceRuleRepository;
    
    @Override
    @Transactional
    public ComplianceRule createRule(ComplianceRule rule) {
        return complianceRuleRepository.save(rule);
    }
    
    @Override
    public List<ComplianceRule> getAllRules() {
        return complianceRuleRepository.findAll();
    }
    
    @Override
    public ComplianceRule getRule(Long id) {
        return complianceRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ComplianceRule not found"));
    }
}