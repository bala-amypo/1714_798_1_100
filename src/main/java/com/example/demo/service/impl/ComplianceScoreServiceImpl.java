// src/main/java/com/example/demo/service/ComplianceScoreServiceImpl.java
package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.util.ComplianceScoringEngine;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplianceScoreServiceImpl implements ComplianceScoreService {
    
    private final VendorRepository vendorRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final VendorDocumentRepository vendorDocumentRepository;
    private final ComplianceScoreRepository complianceScoreRepository;
    private final ComplianceScoringEngine scoringEngine;
    
    public ComplianceScoreServiceImpl(VendorRepository vendorRepository,
                                     DocumentTypeRepository documentTypeRepository,
                                     VendorDocumentRepository vendorDocumentRepository,
                                     ComplianceScoreRepository complianceScoreRepository,
                                     ComplianceScoringEngine scoringEngine) {
        this.vendorRepository = vendorRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.vendorDocumentRepository = vendorDocumentRepository;
        this.complianceScoreRepository = complianceScoreRepository;
        this.scoringEngine = scoringEngine;
    }
    
    @Override
    public ComplianceScore evaluateVendor(Long vendorId) {
        // Load vendor
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + vendorId));
        
        // Get required document types
        List<DocumentType> requiredTypes = documentTypeRepository.findByRequiredTrue();
        
        // Get vendor documents
        List<VendorDocument> vendorDocuments = vendorDocumentRepository.findByVendorId(vendorId);
        
        // Calculate score using scoring engine
        Double score = scoringEngine.calculateScore(vendorDocuments, requiredTypes);
        
        // Validate score
        if (score < 0) {
            throw new ValidationException("Compliance score cannot be negative");
        }
        
        // Get or create compliance score
        ComplianceScore complianceScore = complianceScoreRepository.findByVendorId(vendorId)
                .orElse(new ComplianceScore());
        
        // Update score
        complianceScore.setVendor(vendor);
        complianceScore.setScoreValue(score);
        complianceScore.setLastEvaluated(LocalDateTime.now());
        
        return complianceScoreRepository.save(complianceScore);
    }
    
    @Override
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendorId(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance score not found for vendor id: " + vendorId));
    }
    
    @Override
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
}