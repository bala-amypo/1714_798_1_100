// src/main/java/com/example/demo/controller/ComplianceRuleController.java
package com.example.demo.controller;

import com.example.demo.model.ComplianceRule;
import com.example.demo.service.ComplianceRuleService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/compliance-rules")
public class ComplianceRuleController {
    
    private final ComplianceRuleService complianceRuleService;
    
    public ComplianceRuleController(ComplianceRuleService complianceRuleService) {
        this.complianceRuleService = complianceRuleService;
    }
    
    @PostMapping
    public ComplianceRule createRule(@RequestBody ComplianceRule rule) {
        return complianceRuleService.createRule(rule);
    }
    
    @GetMapping
    public List<ComplianceRule> getAllRules() {
        return complianceRuleService.getAllRules();
    }
    
    @GetMapping("/{id}")
    public ComplianceRule getRule(@PathVariable Long id) {
        return complianceRuleService.getRule(id);
    }
}