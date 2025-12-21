package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.ComplianceRule;
import com.example.demo.repository.ComplianceRuleRepository;
import com.example.demo.service.ComplianceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ComplianceRuleServiceImpl implements ComplianceRuleService {
    
    @Autowired
    private ComplianceRuleRepository complianceRuleRepository;
    
    @Override
    public ComplianceRule createRule(ComplianceRule rule) {
        if (rule.getRuleName() != null && 
            complianceRuleRepository.findAll().stream()
                .anyMatch(r -> r.getRuleName().equals(rule.getRuleName()))) {
            throw new ValidationException("Rule name already exists: " + rule.getRuleName());
        }
        return complianceRuleRepository.save(rule);
    }
    
    @Override
    public ComplianceRule getRule(Long id) {
        return complianceRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance rule not found with id: " + id));
    }
    
    @Override
    public List<ComplianceRule> getAllRules() {
        return complianceRuleRepository.findAll();
    }
    
    @Override
    public void deleteRule(Long id) {
        if (!complianceRuleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Compliance rule not found with id: " + id);
        }
        complianceRuleRepository.deleteById(id);
    }
}