package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.util.ComplianceScoringEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ComplianceScoreServiceImpl implements ComplianceScoreService {
    
    private final VendorRepository vendorRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final VendorDocumentRepository vendorDocumentRepository;
    private final ComplianceScoreRepository complianceScoreRepository;
    private final ComplianceScoringEngine scoringEngine;
    
    @Autowired
    public ComplianceScoreServiceImpl(
            VendorRepository vendorRepository,
            DocumentTypeRepository documentTypeRepository,
            VendorDocumentRepository vendorDocumentRepository,
            ComplianceScoreRepository complianceScoreRepository) {
        this.vendorRepository = vendorRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.vendorDocumentRepository = vendorDocumentRepository;
        this.complianceScoreRepository = complianceScoreRepository;
        this.scoringEngine = new ComplianceScoringEngine();
    }
    
    @Override
    public ComplianceScore evaluateVendor(Long vendorId) {
        // Load vendor
        Vendor vendor = vendorRepository.findById(vendorId)
            .orElseThrow(() -> new RuntimeException("Vendor not found"));
        
        // Load required document types
        List<DocumentType> requiredTypes = documentTypeRepository.findByRequiredTrue();
        
        // Load vendor documents
        List<VendorDocument> vendorDocuments = vendorDocumentRepository.findByVendor(vendor);
        
        // Compute score using scoring engine
        double score = scoringEngine.calculateScore(vendor, requiredTypes, vendorDocuments);
        
        // Validate score
        if (score < 0) {
            throw new RuntimeException("Compliance score cannot be negative");
        }
        
        // Get rating
        String rating = scoringEngine.deriveRating(score);
        
        // Create or update ComplianceScore
        ComplianceScore complianceScore = complianceScoreRepository.findByVendorId(vendorId)
            .orElse(new ComplianceScore());
        
        complianceScore.setVendor(vendor);
        complianceScore.setScoreValue(score);
        complianceScore.setRating(rating);
        
        return complianceScoreRepository.save(complianceScore);
    }
    
    @Override
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendorId(vendorId)
            .orElseThrow(() -> new RuntimeException("Score not found"));
    }
    
    @Override
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
}