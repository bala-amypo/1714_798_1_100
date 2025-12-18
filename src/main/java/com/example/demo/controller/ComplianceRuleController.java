package com.example.demo.controller;

import com.example.demo.model.ComplianceRule;
import com.example.demo.service.ComplianceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compliance-rules")
public class ComplianceRuleController {

    private final ComplianceRuleService complianceRuleService;

    @Autowired
    public ComplianceRuleController(ComplianceRuleService complianceRuleService) {
        this.complianceRuleService = complianceRuleService;
    }

    /**
     * Create a new compliance rule
     */
    @PostMapping
    public ResponseEntity<ComplianceRule> createRule(@Valid @RequestBody ComplianceRule rule) {
        try {
            ComplianceRule createdRule = complianceRuleService.createRule(rule);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRule);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Save/Update a compliance rule
     */
    @PutMapping("/{id}")
    public ResponseEntity<ComplianceRule> updateRule(@PathVariable Long id, 
                                                     @Valid @RequestBody ComplianceRule rule) {
        try {
            // Ensure the ID in the path matches the rule ID if present in body
            if (rule.getId() != null && !rule.getId().equals(id)) {
                return ResponseEntity.badRequest().build();
            }
            
            rule.setId(id);
            ComplianceRule updatedRule = complianceRuleService.save(rule);
            return ResponseEntity.ok(updatedRule);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all compliance rules
     */
    @GetMapping
    public ResponseEntity<List<ComplianceRule>> getAllRules() {
        try {
            List<ComplianceRule> rules = complianceRuleService.getAllRules();
            return ResponseEntity.ok(rules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all compliance rules (alternative endpoint)
     */
    @GetMapping("/all")
    public ResponseEntity<List<ComplianceRule>> findAll() {
        try {
            List<ComplianceRule> rules = complianceRuleService.findAll();
            return ResponseEntity.ok(rules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get compliance rule by ID (using service method that returns Optional)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ComplianceRule> getRuleById(@PathVariable Long id) {
        try {
            Optional<ComplianceRule> rule = complianceRuleService.findById(id);
            return rule.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get compliance rule by ID (alternative endpoint using getRule method)
     */
    @GetMapping("/rule/{id}")
    public ResponseEntity<ComplianceRule> getRule(@PathVariable Long id) {
        try {
            ComplianceRule rule = complianceRuleService.getRule(id);
            if (rule == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(rule);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete a compliance rule
     * Note: You'll need to add a delete method to your service interface
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        try {
            // Check if rule exists
            Optional<ComplianceRule> existingRule = complianceRuleService.findById(id);
            if (existingRule.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            // You'll need to add a delete method to your service interface
            // complianceRuleService.delete(id);
            
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Partial update for compliance rule
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ComplianceRule> partialUpdateRule(@PathVariable Long id, 
                                                           @RequestBody ComplianceRule ruleUpdates) {
        try {
            Optional<ComplianceRule> existingRuleOpt = complianceRuleService.findById(id);
            if (existingRuleOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            ComplianceRule existingRule = existingRuleOpt.get();
            
            // Apply partial updates - only update non-null fields
            if (ruleUpdates.getName() != null) {
                existingRule.setName(ruleUpdates.getName());
            }
            if (ruleUpdates.getDescription() != null) {
                existingRule.setDescription(ruleUpdates.getDescription());
            }
            if (ruleUpdates.getRuleType() != null) {
                existingRule.setRuleType(ruleUpdates.getRuleType());
            }
            if (ruleUpdates.getStatus() != null) {
                existingRule.setStatus(ruleUpdates.getStatus());
            }
            // Add more fields as needed
            
            ComplianceRule updatedRule = complianceRuleService.save(existingRule);
            return ResponseEntity.ok(updatedRule);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}