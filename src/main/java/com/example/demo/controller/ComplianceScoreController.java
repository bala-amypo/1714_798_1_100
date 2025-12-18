package com.example.demo.controller;

import com.example.demo.model.ComplianceScore;
import com.example.demo.service.ComplianceScoreService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/compliance-scores")
@Tag(name = "Compliance Scores")
public class ComplianceScoreController {

    private final ComplianceScoreService service;

    public ComplianceScoreController(ComplianceScoreService service) {
        this.service = service;
    }

    @PostMapping("/evaluate/{vendorId}")
    public ComplianceScore evaluate(@PathVariable Long vendorId) {
        return service.evaluateVendor(vendorId);
    }

    @GetMapping("/{vendorId}")
    public ComplianceScore get(@PathVariable Long vendorId) {
        return service.getScore(vendorId);
    }

    @GetMapping
    public List<ComplianceScore> getAll() {
        return service.getAllScores();
    }
}
