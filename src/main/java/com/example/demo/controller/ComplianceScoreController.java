package com.example.demo.controller;

import com.example.demo.model.ComplianceScore;
import com.example.demo.service.ComplianceScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance-scores")
@RequiredArgsConstructor
public class ComplianceScoreController {
    
    private final ComplianceScoreService complianceScoreService;
    
    @PostMapping("/evaluate")
    public ResponseEntity<ComplianceScore> evaluateVendor(@RequestParam Long vendorId) {
        return ResponseEntity.ok(complianceScoreService.evaluateVendor(vendorId));
    }
    
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<ComplianceScore> getScore(@PathVariable Long vendorId) {
        return ResponseEntity.ok(complianceScoreService.getScore(vendorId));
    }
    
    @GetMapping
    public ResponseEntity<List<ComplianceScore>> getAllScores() {
        return ResponseEntity.ok(complianceScoreService.getAllScores());
    }
}