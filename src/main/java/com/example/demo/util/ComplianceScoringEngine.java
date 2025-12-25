package com.example.demo.util;

import com.example.demo.model.DocumentType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComplianceScoringEngine {
    
    // MUST match test: calculateScore(List<DocumentType>, List<DocumentType>)
    public double calculateScore(List<DocumentType> requiredTypes, List<DocumentType> vendorDocuments) {
        if (requiredTypes == null || requiredTypes.isEmpty()) {
            return 100.0;
        }
        
        double totalWeight = 0;
        double earnedWeight = 0;
        
        for (DocumentType requiredType : requiredTypes) {
            if (requiredType.getWeight() != null) {
                totalWeight += requiredType.getWeight();
            }
            
            boolean hasDocument = false;
            if (vendorDocuments != null) {
                for (DocumentType vendorDoc : vendorDocuments) {
                    if (vendorDoc != null && vendorDoc.getId() != null && 
                        requiredType.getId() != null && 
                        vendorDoc.getId().equals(requiredType.getId())) {
                        hasDocument = true;
                        break;
                    }
                }
            }
            
            if (hasDocument && requiredType.getWeight() != null) {
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