package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
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
    
    // Correct constructor according to constraints
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
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        
        // Get all vendor documents
        List<VendorDocument> vendorDocuments = vendorDocumentRepository.findByVendor_Id(vendorId);
        
        // Get all compliance rules
        List<ComplianceRule> rules = complianceRuleRepository.findAll();
        
        // Calculate score based on documents and rules
        double score = calculateScore(vendorDocuments, rules);
        
        // Determine rating
        String rating = determineRating(score);
        
        // Save or update compliance score
        ComplianceScore complianceScore = complianceScoreRepository.findByVendor_Id(vendorId)
                .orElse(new ComplianceScore());
        
        complianceScore.setVendor(vendor);
        complianceScore.setScoreValue(score);
        complianceScore.setRating(rating);
        complianceScore.setLastEvaluated(LocalDateTime.now());
        
        if (score < 0) {
            throw new RuntimeException("Compliance score cannot be negative");
        }
        
        return complianceScoreRepository.save(complianceScore);
    }
    
    private double calculateScore(List<VendorDocument> vendorDocuments, List<ComplianceRule> rules) {
        double score = 100.0; // Start with perfect score
        
        // Apply rules to adjust score
        for (ComplianceRule rule : rules) {
            switch (rule.getMatchType()) {
                case "EXPIRY_CHECK":
                    score = applyExpiryCheckRule(score, vendorDocuments, rule);
                    break;
                case "DOCUMENT_REQUIRED":
                    score = applyDocumentRequiredRule(score, vendorDocuments, rule);
                    break;
                case "WEIGHTED_SCORE":
                    score = applyWeightedScoreRule(score, vendorDocuments, rule);
                    break;
            }
        }
        
        return Math.max(0, Math.min(100, score)); // Ensure score is between 0-100
    }
    
    private double applyExpiryCheckRule(double currentScore, List<VendorDocument> documents, ComplianceRule rule) {
        // Check for expired documents
        long expiredCount = documents.stream()
                .filter(doc -> doc.getExpiryDate() != null && 
                              doc.getExpiryDate().isBefore(java.time.LocalDate.now()))
                .count();
        
        if (expiredCount > 0) {
            currentScore -= (expiredCount * rule.getThreshold());
        }
        return currentScore;
    }
    
    private double applyDocumentRequiredRule(double currentScore, List<VendorDocument> documents, ComplianceRule rule) {
        // Check if required documents are present
        // This is a simplified version - you'll need to adjust based on your actual DocumentType logic
        if (documents.isEmpty()) {
            currentScore -= rule.getThreshold();
        }
        return currentScore;
    }
    
    private double applyWeightedScoreRule(double currentScore, List<VendorDocument> documents, ComplianceRule rule) {
        // Apply weighted scoring
        double weightedScore = documents.stream()
                .filter(VendorDocument::getIsValid)
                .count() * rule.getThreshold();
        
        return currentScore + weightedScore;
    }
    
    private String determineRating(double score) {
        if (score >= 90) {
            return "EXCELLENT";
        } else if (score >= 75) {
            return "GOOD";
        } else if (score >= 50) {
            return "POOR";
        } else {
            return "NON_COMPLIANT";
        }
    }
    
    @Override
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendor_Id(vendorId)
                .orElseThrow(() -> new RuntimeException("Score not found for vendor"));
    }
    
    @Override
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
}