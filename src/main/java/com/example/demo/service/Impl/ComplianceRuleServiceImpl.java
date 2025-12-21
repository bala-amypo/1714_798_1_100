package com.example.demo.service;
import com.example.demo.model.ComplianceRule;
import com.example.demo.repository.ComplianceRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ComplianceRuleServiceImpl implements ComplianceRuleService {
    
    private final ComplianceRuleRepository complianceRuleRepository;
    
    @Autowired
    public ComplianceRuleServiceImpl(ComplianceRuleRepository complianceRuleRepository) {
        this.complianceRuleRepository = complianceRuleRepository;
    }
    
    @Override
    public ComplianceRule createRule(ComplianceRule rule) {
        if (rule.getThreshold() < 0) {
            rule.setThreshold(0.0);
        }
        
        return complianceRuleRepository.save(rule);
    }
    
    @Override
    public List<ComplianceRule> getAllRules() {
        return complianceRuleRepository.findAll();
    }
    
    @Override
    public ComplianceRule getRule(Long id) {
        return complianceRuleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Compliance rule not found with ID: " + id));
    }
    
    @Override
    public ComplianceRule updateRule(Long id, ComplianceRule rule) {
        ComplianceRule existingRule = getRule(id);
        
        if (rule.getRuleName() != null && !rule.getRuleName().isEmpty()) {
            existingRule.setRuleName(rule.getRuleName());
        }
        if (rule.getRuleDescription() != null) {
            existingRule.setRuleDescription(rule.getRuleDescription());
        }
        if (rule.getMatchType() != null && !rule.getMatchType().isEmpty()) {
            existingRule.setMatchType(rule.getMatchType());
        }
        if (rule.getThreshold() != null) {
            existingRule.setThreshold(rule.getThreshold());
            if (existingRule.getThreshold() < 0) {
                existingRule.setThreshold(0.0);
            }
        }
        
        return complianceRuleRepository.save(existingRule);
    }
    
    @Override
    public void deleteRule(Long id) {
        ComplianceRule rule = getRule(id);
        complianceRuleRepository.delete(rule);
    }
}