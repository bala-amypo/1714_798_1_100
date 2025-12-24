package com.example.demo.controller;

import com.example.demo.dto.ComplianceScoreDTO;
import com.example.demo.model.ComplianceScore;
import com.example.demo.service.ComplianceScoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/compliance-scores")
public class ComplianceScoreController {
    
    private final ComplianceScoreService complianceScoreService;
    
    public ComplianceScoreController(ComplianceScoreService complianceScoreService) {
        this.complianceScoreService = complianceScoreService;
    }
    
    @PostMapping("/evaluate")
    public ResponseEntity<ComplianceScoreDTO> evaluateVendor(@RequestParam Long vendorId) {
        ComplianceScore score = complianceScoreService.evaluateVendor(vendorId);
        return new ResponseEntity<>(convertToDTO(score), HttpStatus.CREATED);
    }
    
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<ComplianceScoreDTO> getScore(@PathVariable Long vendorId) {
        ComplianceScore score = complianceScoreService.getScore(vendorId);
        return ResponseEntity.ok(convertToDTO(score));
    }
    
    @GetMapping
    public ResponseEntity<List<ComplianceScoreDTO>> getAllScores() {
        List<ComplianceScore> scores = complianceScoreService.getAllScores();
        List<ComplianceScoreDTO> dtos = scores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    private ComplianceScoreDTO convertToDTO(ComplianceScore score) {
        ComplianceScoreDTO dto = new ComplianceScoreDTO();
        dto.setId(score.getId());
        dto.setVendorId(score.getVendor().getId());
        dto.setVendorName(score.getVendor().getVendorName());
        dto.setScoreValue(score.getScoreValue());
        dto.setRating(score.getRating());
        dto.setLastEvaluated(score.getLastEvaluated().toString());
        return dto;
    }
}