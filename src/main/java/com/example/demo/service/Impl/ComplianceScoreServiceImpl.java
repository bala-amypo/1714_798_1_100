package com.example.demo.service;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ComplianceScoreServiceImpl implements ComplianceScoreService {
    
    private final VendorRepository vendorRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final VendorDocumentRepository vendorDocumentRepository;
    private final ComplianceScoreRepository complianceScoreRepository;
    
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
    }
    
    @Override
    public ComplianceScore evaluateVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
            .orElseThrow(() -> new RuntimeException("Vendor not found with ID: " + vendorId));
        
        List<DocumentType> requiredTypes = documentTypeRepository.findByRequiredTrue();
        List<VendorDocument> vendorDocuments = vendorDocumentRepository.findByVendor(vendor);
        
        double score = calculateComplianceScore(requiredTypes, vendorDocuments);
        
        if (score < 0) {
            throw new RuntimeException("Compliance score cannot be negative");
        }
        
        String rating = deriveRating(score);
        
        ComplianceScore complianceScore = complianceScoreRepository.findByVendorId(vendorId)
            .orElse(new ComplianceScore());
        
        complianceScore.setVendor(vendor);
        complianceScore.setScoreValue(score);
        complianceScore.setRating(rating);
        
        return complianceScoreRepository.save(complianceScore);
    }
    
    private double calculateComplianceScore(List<DocumentType> requiredTypes, 
                                          List<VendorDocument> vendorDocuments) {
        if (requiredTypes.isEmpty()) {
            return 100.0;
        }
        
        double totalWeight = 0.0;
        double achievedWeight = 0.0;
        
        for (DocumentType requiredType : requiredTypes) {
            totalWeight += requiredType.getWeight();
            
            boolean hasValidDocument = vendorDocuments.stream()
                .filter(doc -> doc.getDocumentType().getId().equals(requiredType.getId()))
                .anyMatch(doc -> doc.getIsValid() == null || doc.getIsValid());
            
            if (hasValidDocument) {
                achievedWeight += requiredType.getWeight();
            }
        }
        
        if (totalWeight == 0) {
            return 100.0;
        }
        
        double score = (achievedWeight / totalWeight) * 100.0;
        
        long expiredCount = vendorDocuments.stream()
            .filter(doc -> doc.getExpiryDate() != null && 
                          doc.getExpiryDate().isBefore(LocalDate.now()))
            .count();
        
        if (expiredCount > 0) {
            score -= (expiredCount * 10);
        }
        
        return Math.max(0.0, Math.min(100.0, score));
    }
    
    private String deriveRating(double score) {
        if (score >= 90) {
            return "EXCELLENT";
        } else if (score >= 70) {
            return "GOOD";
        } else if (score >= 50) {
            return "POOR";
        } else {
            return "NONCOMPLIANT";
        }
    }
    
    @Override
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendorId(vendorId)
            .orElseThrow(() -> new RuntimeException("Score not found for vendor ID: " + vendorId));
    }
    
    @Override
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
    
    @Override
    public ComplianceScore updateScore(Long vendorId, ComplianceScore score) {
        ComplianceScore existingScore = getScore(vendorId);
        
        if (score.getScoreValue() != null) {
            if (score.getScoreValue() < 0) {
                throw new RuntimeException("Compliance score cannot be negative");
            }
            existingScore.setScoreValue(score.getScoreValue());
            existingScore.setRating(deriveRating(score.getScoreValue()));
        }
        
        if (score.getRating() != null && !score.getRating().isEmpty()) {
            existingScore.setRating(score.getRating());
        }
        
        return complianceScoreRepository.save(existingScore);
    }
    
    @Override
    public void deleteScore(Long vendorId) {
        ComplianceScore score = getScore(vendorId);
        complianceScoreRepository.delete(score);
    }
}