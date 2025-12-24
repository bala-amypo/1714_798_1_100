package com.example.demo.service.impl;

import com.example.demo.model.ComplianceRule;
import com.example.demo.repository.ComplianceRuleRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.ComplianceRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ComplianceRuleServiceImpl implements ComplianceRuleService {
    
    private final ComplianceRuleRepository complianceRuleRepository;
    
    public ComplianceRuleServiceImpl(ComplianceRuleRepository complianceRuleRepository) {
        this.complianceRuleRepository = complianceRuleRepository;
    }
    
    @Override
    public ComplianceRule createRule(ComplianceRule rule) {
        return complianceRuleRepository.save(rule);
    }
    
    @Override
    public ComplianceRule getRule(Long id) {
        return complianceRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ComplianceRule not found with id: " + id));
    }
    
    @Override
    public List<ComplianceRule> getAllRules() {
        return complianceRuleRepository.findAll();
    }
    
    @Override
    public List<ComplianceRule> getActiveRules() {
        return complianceRuleRepository.findByIsActiveTrue();
    }
    
    @Override
    public ComplianceRule updateRule(Long id, ComplianceRule ruleDetails) {
        ComplianceRule rule = getRule(id);
        rule.setRuleName(ruleDetails.getRuleName());
        rule.setMatchType(ruleDetails.getMatchType());
        rule.setThreshold(ruleDetails.getThreshold());
        rule.setDescription(ruleDetails.getDescription());
        rule.setIsActive(ruleDetails.getIsActive());
        return complianceRuleRepository.save(rule);
    }
    
    @Override
    public void deleteRule(Long id) {
        ComplianceRule rule = getRule(id);
        complianceRuleRepository.delete(rule);
    }
    
    @Override
    public ComplianceRule toggleRuleStatus(Long id) {
        ComplianceRule rule = getRule(id);
        rule.setIsActive(!rule.getIsActive());
        return complianceRuleRepository.save(rule);
    }
}