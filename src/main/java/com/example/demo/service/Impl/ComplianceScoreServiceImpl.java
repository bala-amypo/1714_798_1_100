package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.ComplianceScoreService;
import com.example.demo.util.ComplianceScoringEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
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
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + vendorId));
        
        List<DocumentType> requiredTypes = documentTypeRepository.findByRequiredTrue();
        List<VendorDocument> vendorDocuments = vendorDocumentRepository.findByVendor(vendor);
        
        double scoreValue = ComplianceScoringEngine.calculateScore(requiredTypes, vendorDocuments);
        
        if (scoreValue < 0) {
            throw new ValidationException("Compliance score cannot be negative");
        }
        
        String rating = ComplianceScoringEngine.deriveRating(scoreValue);
        
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
    
    @Override
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendorId(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found for vendor id: " + vendorId));
    }
    
    @Override
    public List<ComplianceScore> getAllScores() {
        return complianceScoreRepository.findAll();
    }
    
    @Override
    public void deleteScore(Long id) {
        if (!complianceScoreRepository.existsById(id)) {
            throw new ResourceNotFoundException("Compliance score not found with id: " + id);
        }
        complianceScoreRepository.deleteById(id);
    }
}