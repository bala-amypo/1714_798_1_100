package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.ComplianceScoreService;
import com.example.demo.util.ComplianceScoringEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplianceScoreServiceImpl implements ComplianceScoreService {
    
    private final VendorRepository vendorRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final VendorDocumentRepository vendorDocumentRepository;
    private final ComplianceScoreRepository complianceScoreRepository;
    private final ComplianceScoringEngine scoringEngine = new ComplianceScoringEngine();
    
    @Override
    @Transactional
    public ComplianceScore evaluateVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
        
        List<DocumentType> requiredTypes = documentTypeRepository.findByRequiredTrue();
        List<VendorDocument> vendorDocuments = vendorDocumentRepository.findByVendor(vendor);
        
        double score = scoringEngine.calculateScore(requiredTypes, vendorDocuments);
        
        if (score < 0) {
            throw new ValidationException("Compliance score cannot be negative");
        }
        
        String rating = scoringEngine.deriveRating(score);
        
        Optional<ComplianceScore> existingScore = complianceScoreRepository.findByVendorId(vendorId);
        ComplianceScore complianceScore;
        
        if (existingScore.isPresent()) {
            complianceScore = existingScore.get();
            complianceScore.setScoreValue(score);
            complianceScore.setRating(rating);
        } else {
            complianceScore = new ComplianceScore();
            complianceScore.setVendor(vendor);
            complianceScore.setScoreValue(score);
            complianceScore.setRating(rating);
        }
        
        complianceScore.setLastEvaluated(LocalDateTime.now());
        
        return complianceScoreRepository.save(complianceScore);
    }
    
    @Override
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendorId(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found"));
    }
    
    @Override
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
}