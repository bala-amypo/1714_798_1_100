package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.ComplianceScoreService;
import com.example.demo.util.ComplianceScoringEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
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
        
        double scoreValue = ComplianceScoringEngine.calculateScore(requiredTypes, vendorDocuments);
        
        if (scoreValue < 0) {
            throw new IllegalArgumentException("Compliance score cannot be negative");
        }
        
        String rating = ComplianceScoringEngine.deriveRating(scoreValue);
        
        Optional<ComplianceScore> existingScore = complianceScoreRepository.findByVendorId(vendorId);
        ComplianceScore complianceScore;
        
        if (existingScore.isPresent()) {
            complianceScore = existingScore.get();
            complianceScore.setScoreValue(scoreValue);
            complianceScore.setRating(rating);
            complianceScore.setEvaluationDate(LocalDateTime.now());
        } else {
            complianceScore = new ComplianceScore();
            complianceScore.setVendor(vendor);
            complianceScore.setScoreValue(scoreValue);
            complianceScore.setRating(rating);
            complianceScore.setEvaluationDate(LocalDateTime.now());
        }
        
        return complianceScoreRepository.save(complianceScore);
    }
    
    @Override
    public ComplianceScore getScore(Long vendorId) {
        return complianceScoreRepository.findByVendorId(vendorId)
                .orElseThrow(() -> new NoSuchElementException("Score not found for vendor id: " + vendorId));
    }
    
    @Override
    public ComplianceScore getScoreByVendorId(Long vendorId) {
        return getScore(vendorId);
    }
    
    @Override
    public ComplianceScore getScoreById(Long id) {
        return complianceScoreRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Compliance score not found with id: " + id));
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