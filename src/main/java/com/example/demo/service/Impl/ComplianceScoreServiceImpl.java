package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.util.ComplianceScoringEngine;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplianceScoreServiceImpl {
    
    private final VendorRepository vendorRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final VendorDocumentRepository vendorDocumentRepository;
    private final ComplianceScoreRepository complianceScoreRepository;
    private final ComplianceScoringEngine scoringEngine = new ComplianceScoringEngine();
    
    public ComplianceScoreServiceImpl(
            VendorRepository vendorRepository,
            DocumentTypeRepository documentTypeRepository,
            VendorDocumentRepository vendorDocumentRepository,
            ComplianceScoreRepository complianceScoreRepository) {
        this.vendorRepository = vendorRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.vendorDocumentRepository = vendorDocumentRepository;
        this.complianceScoreRepository = complianceScoreRepository;
    }
    
    public ComplianceScore evaluateVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
        
        List<DocumentType> requiredTypes = documentTypeRepository.findByRequiredTrue();
        List<VendorDocument> vendorDocuments = vendorDocumentRepository.findByVendor(vendor);
        
        double score = scoringEngine.calculateScore(requiredTypes, vendorDocuments);
        String rating = scoringEngine.deriveRating(score);
        
        ComplianceScore complianceScore = complianceScoreRepository.findByVendor_Id(vendorId)
                .orElse(new ComplianceScore());
        
        complianceScore.setVendor(vendor);
        complianceScore.setScoreValue(score);
        complianceScore.setRating(rating);
        complianceScore.setEvaluatedAt(LocalDateTime.now());
        
        return complianceScoreRepository.save(complianceScore);
    }
    
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendor_Id(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found for vendor id: " + vendorId));
    }
}