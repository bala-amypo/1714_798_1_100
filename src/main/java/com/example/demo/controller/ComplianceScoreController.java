package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.ComplianceScore;
import com.example.demo.service.ComplianceScoreService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compliance-scores")
public class ComplianceScoreController {

    @Autowired
    ComplianceScoreService complianceScoreService;

    @PostMapping("/evaluate")
    public ComplianceScore evaluateVendor(@RequestParam Long vendorId) {
        return complianceScoreService.evaluateVendor(vendorId);
    }

    @GetMapping
    public List<ComplianceScore> getAllScores() {
        return complianceScoreService.getAllScores();
    }

    @GetMapping("/vendor/{vendorId}")
    public Optional<ComplianceScore> getScoreByVendor(@PathVariable Long vendorId) {
        return complianceScoreService.getScoreByVendorId(vendorId);
    }

    @GetMapping("/{id}")
    public Optional<ComplianceScore> getScore(@PathVariable Long id) {
        return complianceScoreService.getScoreById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteScore(@PathVariable Long id) {
        Optional<ComplianceScore> score = complianceScoreService.getScoreById(id);
        if (score.isPresent()) {
            complianceScoreService.deleteScore(id);
            return "Compliance Score Deleted Successfully";
        }
        return "Compliance Score not found";
    }
}