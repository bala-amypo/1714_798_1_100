package com.example.demo.controller;

import com.example.demo.model.ComplianceScore;
import com.example.demo.service.ComplianceScoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/compliance-scores")
@Tag(name = "Compliance Scores", description = "Compliance score management endpoints")
public class ComplianceScoreController {
    
    private final ComplianceScoreService complianceScoreService;
    
    public ComplianceScoreController(ComplianceScoreService complianceScoreService) {
        this.complianceScoreService = complianceScoreService;
    }
    
    @PostMapping("/evaluate/{vendorId}")
    @Operation(summary = "Evaluate compliance score for a vendor")
    public ResponseEntity<ComplianceScore> evaluateVendor(@PathVariable Long vendorId) {
        ComplianceScore score = complianceScoreService.evaluateVendor(vendorId);
        return new ResponseEntity<>(score, HttpStatus.CREATED);
    }
    
    @GetMapping("/{vendorId}")
    @Operation(summary = "Get compliance score for a vendor")
    public ResponseEntity<ComplianceScore> getScore(@PathVariable Long vendorId) {
        ComplianceScore score = complianceScoreService.getScore(vendorId);
        return ResponseEntity.ok(score);
    }
    
    @GetMapping
    @Operation(summary = "Get all compliance scores")
    public ResponseEntity<List<ComplianceScore>> getAllScores() {
        List<ComplianceScore> scores = complianceScoreService.getAllScores();
        return ResponseEntity.ok(scores);
    }
}