package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.util.ComplianceScoringEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ComplianceScoreServiceImpl {
    
    private final VendorRepository vendorRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final VendorDocumentRepository vendorDocumentRepository;
    private final ComplianceScoreRepository complianceScoreRepository;
    private final ComplianceScoringEngine scoringEngine;
    
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
    
    public ComplianceScore evaluateVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + vendorId));
        
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
        complianceScore.setValidUntil(LocalDateTime.now().plusMonths(1));
        
        // Calculate detailed metrics
        int totalWeight = requiredTypes.stream().mapToInt(DocumentType::getWeight).sum();
        int achievedWeight = 0;
        int totalRequired = requiredTypes.size();
        int totalCompliant = 0;
        
        for (DocumentType docType : requiredTypes) {
            boolean hasValidDocument = vendorDocuments.stream()
                    .anyMatch(doc -> 
                        doc.getDocumentType().getId().equals(docType.getId()) && 
                        Boolean.TRUE.equals(doc.getIsValid())
                    );
            
            if (hasValidDocument) {
                achievedWeight += docType.getWeight();
                totalCompliant++;
            }
        }
        
        complianceScore.setTotalWeight(totalWeight);
        complianceScore.setAchievedWeight(achievedWeight);
        complianceScore.setTotalRequired(totalRequired);
        complianceScore.setTotalCompliant(totalCompliant);
        
        return complianceScoreRepository.save(complianceScore);
    }
    
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendor_Id(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found for vendor id: " + vendorId));
    }
    
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
    
    public List<ComplianceScore> getScoresByRating(String rating) {
        return complianceScoreRepository.findByRating(rating);
    }
    
    public List<ComplianceScore> getScoresAboveThreshold(Double minScore) {
        return complianceScoreRepository.findByScoreValueGreaterThanEqual(minScore);
    }
    
    public void reevaluateAllVendors() {
        List<Vendor> vendors = vendorRepository.findAll();
        for (Vendor vendor : vendors) {
            evaluateVendor(vendor.getId());
        }
    }
}