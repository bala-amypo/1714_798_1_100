package com.example.demo.controller;

import com.example.demo.model.ComplianceRule;
import com.example.demo.service.ComplianceRuleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance-rules")
@Tag(name = "Compliance Rules")
public class ComplianceRuleController {

    private final ComplianceRuleService service;

    public ComplianceRuleController(ComplianceRuleService service) {
        this.service = service;
    }

    @PostMapping
    public ComplianceRule create(@RequestBody ComplianceRule rule) {
        return service.save(rule);
    }

    @GetMapping
    public List<ComplianceRule> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ComplianceRule getById(@PathVariable Long id) {
        return service.findById(id);
    }
}
