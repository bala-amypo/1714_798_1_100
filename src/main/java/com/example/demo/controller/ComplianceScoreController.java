package com.example.demo.controller;

import com.example.demo.dto.ComplianceScoreDTO;
import com.example.demo.model.ComplianceScore;
import com.example.demo.service.ComplianceScoreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/compliance-scores")
public class ComplianceScoreController {
    
    @Autowired
    private ComplianceScoreService complianceScoreService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping("/evaluate")
    public ResponseEntity<ComplianceScoreDTO> evaluateVendor(@RequestParam Long vendorId) {
        ComplianceScore score = complianceScoreService.evaluateVendor(vendorId);
        return ResponseEntity.ok(convertToDto(score));
    }
    
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<ComplianceScoreDTO> getScore(@PathVariable Long vendorId) {
        ComplianceScore score = complianceScoreService.getScore(vendorId);
        return ResponseEntity.ok(convertToDto(score));
    }
    
    @GetMapping
    public ResponseEntity<List<ComplianceScoreDTO>> getAllScores() {
        List<ComplianceScore> scores = complianceScoreService.getAllScores();
        List<ComplianceScoreDTO> dtos = scores.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    private ComplianceScoreDTO convertToDto(ComplianceScore score) {
        ComplianceScoreDTO dto = modelMapper.map(score, ComplianceScoreDTO.class);
        dto.setVendorId(score.getVendor().getId());
        dto.setVendorName(score.getVendor().getVendorName());
        return dto;
    }
}