package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComplianceScoringEngine {
    
    // Method that matches test expectations
    public double calculateScore(List<DocumentType> requiredTypes, List<DocumentType> vendorDocuments) {
        if (requiredTypes == null || requiredTypes.isEmpty()) {
            return 100.0;
        }
        
        double totalWeight = 0;
        double earnedWeight = 0;
        
        for (DocumentType requiredType : requiredTypes) {
            totalWeight += requiredType.getWeight();
            
            // Check if vendor has this document type
            boolean hasDocument = vendorDocuments.stream()
                    .anyMatch(doc -> doc.getId() != null && 
                                   doc.getId().equals(requiredType.getId()));
            
            if (hasDocument) {
                earnedWeight += requiredType.getWeight();
            }
        }
        
        if (totalWeight == 0) {
            return 100.0;
        }
        
        return (earnedWeight / totalWeight) * 100.0;
    }
    
    public String deriveRating(double score) {
        if (score >= 90) {
            return "EXCELLENT";
        } else if (score >= 70) {
            return "GOOD";
        } else if (score >= 50) {
            return "POOR";
        } else {
            return "NON_COMPLIANT";
        }
    }
}