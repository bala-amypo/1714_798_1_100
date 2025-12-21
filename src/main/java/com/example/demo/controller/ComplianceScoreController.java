package com.example.demo.controller;

import com.example.demo.model.ComplianceScore;
import com.example.demo.service.ComplianceScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/compliance-scores")
public class ComplianceScoreController {
    
    private final ComplianceScoreService complianceScoreService;
    
    @Autowired
    public ComplianceScoreController(ComplianceScoreService complianceScoreService) {
        this.complianceScoreService = complianceScoreService;
    }
    
    @PostMapping("/evaluate")
    public ResponseEntity<ComplianceScore> evaluateVendor(@RequestParam Long vendorId) {
        ComplianceScore score = complianceScoreService.evaluateVendor(vendorId);
        return new ResponseEntity<>(score, HttpStatus.CREATED);
    }
    
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<ComplianceScore> getScoreByVendor(@PathVariable Long vendorId) {
        ComplianceScore score = complianceScoreService.getScore(vendorId);
        return ResponseEntity.ok(score);
    }
    
    @GetMapping
    public ResponseEntity<List<ComplianceScore>> getAllScores() {
        List<ComplianceScore> scores = complianceScoreService.getAllScores();
        return ResponseEntity.ok(scores);
    }
    
    @PutMapping("/vendor/{vendorId}")
    public ResponseEntity<ComplianceScore> updateScore(@PathVariable Long vendorId, @RequestBody ComplianceScore score) {
        ComplianceScore updatedScore = complianceScoreService.updateScore(vendorId, score);
        return ResponseEntity.ok(updatedScore);
    }
    
    @DeleteMapping("/vendor/{vendorId}")
    public ResponseEntity<Void> deleteScore(@PathVariable Long vendorId) {
        complianceScoreService.deleteScore(vendorId);
        return ResponseEntity.noContent().build();
    }
}