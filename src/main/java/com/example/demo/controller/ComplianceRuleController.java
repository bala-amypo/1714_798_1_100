package com.example.demo.controller;
import com.example.demo.model.ComplianceRule;
import com.example.demo.service.ComplianceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/compliance-rules")
public class ComplianceRuleController {
    
    private final ComplianceRuleService complianceRuleService;
    
    @Autowired
    public ComplianceRuleController(ComplianceRuleService complianceRuleService) {
        this.complianceRuleService = complianceRuleService;
    }
    
    @PostMapping
    public ResponseEntity<ComplianceRule> createRule(@RequestBody ComplianceRule rule) {
        ComplianceRule createdRule = complianceRuleService.createRule(rule);
        return new ResponseEntity<>(createdRule, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<ComplianceRule>> getAllRules() {
        List<ComplianceRule> rules = complianceRuleService.getAllRules();
        return ResponseEntity.ok(rules);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ComplianceRule> getRuleById(@PathVariable Long id) {
        ComplianceRule rule = complianceRuleService.getRule(id);
        return ResponseEntity.ok(rule);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ComplianceRule> updateRule(@PathVariable Long id, @RequestBody ComplianceRule rule) {
        ComplianceRule updatedRule = complianceRuleService.updateRule(id, rule);
        return ResponseEntity.ok(updatedRule);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        complianceRuleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
}