package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.*;
import com.example.demo.repository.ComplianceScoreRepository;
import com.example.demo.repository.VendorRepository;
import com.example.demo.repository.VendorDocumentRepository;
import com.example.demo.repository.ComplianceRuleRepository;
import com.example.demo.service.ComplianceScoreService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplianceScoreServiceImpl implements ComplianceScoreService {
    
    private final ComplianceScoreRepository complianceScoreRepository;
    private final VendorRepository vendorRepository;
    private final VendorDocumentRepository vendorDocumentRepository;
    private final ComplianceRuleRepository complianceRuleRepository;
    
    public ComplianceScoreServiceImpl(ComplianceScoreRepository complianceScoreRepository,
                                     VendorRepository vendorRepository,
                                     VendorDocumentRepository vendorDocumentRepository,
                                     ComplianceRuleRepository complianceRuleRepository) {
        this.complianceScoreRepository = complianceScoreRepository;
        this.vendorRepository = vendorRepository;
        this.vendorDocumentRepository = vendorDocumentRepository;
        this.complianceRuleRepository = complianceRuleRepository;
    }
    
    @Override
    public ComplianceScore evaluateVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
        
        // Get all document types
        List<DocumentType> allDocumentTypes = documentTypeRepository.findAll();
        
        // Get vendor's documents
        List<VendorDocument> vendorDocuments = vendorDocumentRepository.findByVendor_Id(vendorId);
        
        // Calculate base score
        double totalWeight = 0.0;
        double achievedWeight = 0.0;
        
        for (DocumentType docType : allDocumentTypes) {
            totalWeight += docType.getWeight();
            
            // Find if vendor has this document type
            VendorDocument matchingDoc = vendorDocuments.stream()
                    .filter(doc -> doc.getDocumentType().getId().equals(docType.getId()))
                    .findFirst()
                    .orElse(null);
            
            if (matchingDoc != null && matchingDoc.getIsValid()) {
                achievedWeight += docType.getWeight();
            } else if (docType.getRequired()) {
                // Penalty for missing required documents
                achievedWeight -= docType.getWeight() * 0.5;
            }
        }
        
        // Calculate percentage score
        double scoreValue = (achievedWeight / Math.max(totalWeight, 1.0)) * 100.0;
        
        // Apply compliance rules
        List<ComplianceRule> rules = complianceRuleRepository.findAll();
        for (ComplianceRule rule : rules) {
            switch (rule.getMatchType()) {
                case "EXPIRY_CHECK":
                    // Check if any required document is expired
                    for (VendorDocument doc : vendorDocuments) {
                        if (doc.getDocumentType().getRequired() && 
                            !doc.getIsValid() && 
                            doc.getExpiryDate() != null) {
                            scoreValue *= 0.7; // 30% penalty for expired required documents
                            break;
                        }
                    }
                    break;
                case "DOCUMENT_REQUIRED":
                    // Check if all required documents are present
                    for (DocumentType docType : allDocumentTypes) {
                        if (docType.getRequired()) {
                            boolean hasRequiredDoc = vendorDocuments.stream()
                                    .anyMatch(doc -> doc.getDocumentType().getId().equals(docType.getId()) 
                                            && doc.getIsValid());
                            if (!hasRequiredDoc) {
                                scoreValue = Math.max(scoreValue - rule.getThreshold(), 0);
                            }
                        }
                    }
                    break;
                case "WEIGHTED_SCORE":
                    if (scoreValue < rule.getThreshold()) {
                        scoreValue *= 0.9; // 10% penalty for falling below threshold
                    }
                    break;
            }
        }
        
        // Ensure score is between 0-100
        scoreValue = Math.max(0.0, Math.min(100.0, scoreValue));
        
        // Determine rating
        String rating;
        if (scoreValue >= 90) {
            rating = "EXCELLENT";
        } else if (scoreValue >= 70) {
            rating = "GOOD";
        } else if (scoreValue >= 50) {
            rating = "POOR";
        } else {
            rating = "NON_COMPLIANT";
        }
        
        // Check for negative score
        if (scoreValue < 0) {
            throw new ValidationException("Compliance score cannot be negative");
        }
        
        // Save or update compliance score
        ComplianceScore existingScore = complianceScoreRepository.findByVendor_Id(vendorId)
                .orElse(null);
        
        if (existingScore != null) {
            existingScore.setScoreValue(scoreValue);
            existingScore.setLastEvaluated(LocalDateTime.now());
            existingScore.setRating(rating);
            return complianceScoreRepository.save(existingScore);
        } else {
            ComplianceScore newScore = new ComplianceScore();
            newScore.setVendor(vendor);
            newScore.setScoreValue(scoreValue);
            newScore.setLastEvaluated(LocalDateTime.now());
            newScore.setRating(rating);
            return complianceScoreRepository.save(newScore);
        }
    }
    
    @Override
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendor_Id(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance Score not found for vendor"));
    }
    
    @Override
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
    
    // Helper method to get document types (need to inject repository)
    private final DocumentTypeRepository documentTypeRepository;
    
    public void setDocumentTypeRepository(DocumentTypeRepository documentTypeRepository) {
        // This is needed for the calculation
        // Note: This is a workaround since constructor signature is fixed
        this.documentTypeRepository = documentTypeRepository;
    }
}