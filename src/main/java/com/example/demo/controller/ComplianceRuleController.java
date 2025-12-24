package com.example.demo.controller;

import com.example.demo.dto.ComplianceRuleDTO;
import com.example.demo.model.ComplianceRule;
import com.example.demo.service.ComplianceRuleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/compliance-rules")
public class ComplianceRuleController {
    
    private final ComplianceRuleService complianceRuleService;
    
    public ComplianceRuleController(ComplianceRuleService complianceRuleService) {
        this.complianceRuleService = complianceRuleService;
    }
    
    @PostMapping
    public ResponseEntity<ComplianceRuleDTO> createRule(@Valid @RequestBody ComplianceRuleDTO ruleDTO) {
        ComplianceRule rule = new ComplianceRule();
        rule.setRuleName(ruleDTO.getRuleName());
        rule.setRuleDescription(ruleDTO.getRuleDescription());
        rule.setMatchType(ruleDTO.getMatchType());
        rule.setThreshold(ruleDTO.getThreshold());
        
        ComplianceRule savedRule = complianceRuleService.createRule(rule);
        return new ResponseEntity<>(convertToDTO(savedRule), HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<ComplianceRuleDTO>> getAllRules() {
        List<ComplianceRule> rules = complianceRuleService.getAllRules();
        List<ComplianceRuleDTO> dtos = rules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ComplianceRuleDTO> getRule(@PathVariable Long id) {
        ComplianceRule rule = complianceRuleService.getRule(id);
        return ResponseEntity.ok(convertToDTO(rule));
    }
    
    private ComplianceRuleDTO convertToDTO(ComplianceRule rule) {
        ComplianceRuleDTO dto = new ComplianceRuleDTO();
        dto.setId(rule.getId());
        dto.setRuleName(rule.getRuleName());
        dto.setRuleDescription(rule.getRuleDescription());
        dto.setMatchType(rule.getMatchType());
        dto.setThreshold(rule.getThreshold());
        return dto;
    }
}