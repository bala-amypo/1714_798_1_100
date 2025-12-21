package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.ComplianceRule;
import com.example.demo.service.ComplianceRuleService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compliance-rules")
public class ComplianceRuleController {

    @Autowired
    ComplianceRuleService complianceRuleService;

    @PostMapping
    public ComplianceRule createRule(@RequestBody ComplianceRule rule) {
        return complianceRuleService.createRule(rule);
    }

    @GetMapping
    public List<ComplianceRule> getAllRules() {
        return complianceRuleService.getAllRules();
    }

    @GetMapping("/{id}")
    public Optional<ComplianceRule> getRule(@PathVariable Long id) {
        return complianceRuleService.getRuleById(id);
    }

    @PutMapping("/{id}")
    public String updateRule(@PathVariable Long id, @RequestBody ComplianceRule newRule) {
        Optional<ComplianceRule> rule = complianceRuleService.getRuleById(id);
        if (rule.isPresent()) {
            newRule.setId(id);
            complianceRuleService.updateRule(newRule);
            return "Compliance Rule Updated Successfully";
        }
        return "Compliance Rule not found";
    }

    @DeleteMapping("/{id}")
    public String deleteRule(@PathVariable Long id) {
        Optional<ComplianceRule> rule = complianceRuleService.getRuleById(id);
        if (rule.isPresent()) {
            complianceRuleService.deleteRule(id);
            return "Compliance Rule Deleted Successfully";
        }
        return "Compliance Rule not found";
    }
}