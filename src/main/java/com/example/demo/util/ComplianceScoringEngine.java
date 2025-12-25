package com.example.demo.util;

import com.example.demo.model.DocumentType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComplianceScoringEngine {
    
    public double calculateScore(List<DocumentType> requiredTypes, List<DocumentType> vendorDocuments) {
        // Test passes List<DocumentType> for both parameters
        if (requiredTypes == null || requiredTypes.isEmpty()) {
            return 100.0;
        }
        
        double totalWeight = 0;
        double earnedWeight = 0;
        
        for (DocumentType requiredType : requiredTypes) {
            Integer weight = requiredType.getWeight();
            if (weight != null) {
                totalWeight += weight;
            }
            
            // Check if vendor has this document
            boolean hasDocument = false;
            if (vendorDocuments != null) {
                for (DocumentType doc : vendorDocuments) {
                    if (doc != null && doc.getId() != null && 
                        requiredType.getId() != null && 
                        doc.getId().equals(requiredType.getId())) {
                        hasDocument = true;
                        break;
                    }
                }
            }
            
            if (hasDocument && weight != null) {
                earnedWeight += weight;
            }
        }
        
        if (totalWeight == 0) {
            return 100.0;
        }
        
        return (earnedWeight / totalWeight) * 100.0;
    }
    
    public String deriveRating(double score) {
        if (score >= 90) return "EXCELLENT";
        if (score >= 70) return "GOOD";
        if (score >= 50) return "POOR";
        return "NON_COMPLIANT";
    }
}