package com.example.demo.controller;

import com.example.demo.dto.ComplianceRuleDTO;
import com.example.demo.model.ComplianceRule;
import com.example.demo.service.ComplianceRuleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/compliance-rules")
public class ComplianceRuleController {
    
    @Autowired
    private ComplianceRuleService complianceRuleService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping
    public ResponseEntity<ComplianceRuleDTO> createRule(@RequestBody ComplianceRule rule) {
        ComplianceRule createdRule = complianceRuleService.createRule(rule);
        return ResponseEntity.ok(modelMapper.map(createdRule, ComplianceRuleDTO.class));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ComplianceRuleDTO> getRule(@PathVariable Long id) {
        ComplianceRule rule = complianceRuleService.getRule(id);
        return ResponseEntity.ok(modelMapper.map(rule, ComplianceRuleDTO.class));
    }
    
    @GetMapping
    public ResponseEntity<List<ComplianceRuleDTO>> getAllRules() {
        List<ComplianceRule> rules = complianceRuleService.getAllRules();
        List<ComplianceRuleDTO> dtos = rules.stream()
                .map(rule -> modelMapper.map(rule, ComplianceRuleDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}