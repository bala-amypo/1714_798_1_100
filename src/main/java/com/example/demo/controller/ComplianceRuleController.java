package com.example.demo.controller;

import com.example.demo.model.ComplianceRule;
import com.example.demo.service.ComplianceRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance-rules")
@RequiredArgsConstructor
public class ComplianceRuleController {
    
    private final ComplianceRuleService complianceRuleService;
    
    @PostMapping
    public ResponseEntity<ComplianceRule> createRule(@RequestBody ComplianceRule rule) {
        return ResponseEntity.ok(complianceRuleService.createRule(rule));
    }
    
    @GetMapping
    public ResponseEntity<List<ComplianceRule>> getAllRules() {
        return ResponseEntity.ok(complianceRuleService.getAllRules());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ComplianceRule> getRule(@PathVariable Long id) {
        return ResponseEntity.ok(complianceRuleService.getRule(id));
    }
}