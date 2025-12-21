package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.ComplianceScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ComplianceScoreServiceImpl implements ComplianceScoreService {
    
    @Autowired
    private VendorRepository vendorRepository;
    
    @Autowired
    private DocumentTypeRepository documentTypeRepository;
    
    @Autowired
    private VendorDocumentRepository vendorDocumentRepository;
    
    @Autowired
    private ComplianceScoreRepository complianceScoreRepository;
    
    @Override
    public ComplianceScore evaluateVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new NoSuchElementException("Vendor not found with id: " + vendorId));
        
        List<DocumentType> requiredTypes = documentTypeRepository.findByRequiredTrue();
        List<VendorDocument> vendorDocuments = vendorDocumentRepository.findByVendor(vendor);
        
        double scoreValue = calculateScore(requiredTypes, vendorDocuments);
        
        if (scoreValue < 0) {
            throw new IllegalArgumentException("Compliance score cannot be negative");
        }
        
        String rating = deriveRating(scoreValue);
        
        Optional<ComplianceScore> existingScore = complianceScoreRepository.findByVendorId(vendorId);
        ComplianceScore complianceScore;
        
        if (existingScore.isPresent()) {
            complianceScore = existingScore.get();
            complianceScore.setScoreValue(scoreValue);
            complianceScore.setRating(rating);
        } else {
            complianceScore = new ComplianceScore(vendor, scoreValue, rating);
        }
        
        return complianceScoreRepository.save(complianceScore);
    }
    
    private double calculateScore(List<DocumentType> requiredTypes, 
                                 List<VendorDocument> vendorDocuments) {
        if (requiredTypes == null || requiredTypes.isEmpty()) {
            return 100.0;
        }
        
        if (vendorDocuments == null || vendorDocuments.isEmpty()) {
            return 0.0;
        }
        
        int totalRequired = requiredTypes.size();
        int validCount = 0;
        
        for (DocumentType requiredType : requiredTypes) {
            VendorDocument document = vendorDocuments.stream()
                .filter(doc -> doc.getDocumentType().getId().equals(requiredType.getId()))
                .findFirst()
                .orElse(null);
            
            if (document != null && isDocumentValid(document)) {
                validCount++;
            }
        }
        
        return (double) validCount / totalRequired * 100;
    }
    
    private String deriveRating(double scoreValue) {
        if (scoreValue >= 90) return "EXCELLENT";
        if (scoreValue >= 75) return "GOOD";
        if (scoreValue >= 60) return "FAIR";
        if (scoreValue >= 40) return "POOR";
        return "NON-COMPLIANT";
    }
    
    private boolean isDocumentValid(VendorDocument document) {
        if (document.getExpiryDate() == null) {
            return true;
        }
        return !document.getExpiryDate().isBefore(LocalDate.now());
    }
    
    @Override
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendorId(vendorId)
                .orElseThrow(() -> new NoSuchElementException("Score not found for vendor id: " + vendorId));
    }
    
    @Override
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
    
    @Override
    public void deleteScore(Long id) {
        if (!complianceScoreRepository.existsById(id)) {
            throw new NoSuchElementException("Compliance score not found with id: " + id);
        }
        complianceScoreRepository.deleteById(id);
    }
}