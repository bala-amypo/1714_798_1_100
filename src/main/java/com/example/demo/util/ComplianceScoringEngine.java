package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComplianceScoringEngine {
    
    // This method signature must match what the test expects
    public double calculateScore(List<DocumentType> requiredTypes, List<DocumentType> vendorDocuments) {
        // The test is passing List<DocumentType> as second parameter
        if (requiredTypes.isEmpty()) {
            return 100.0;
        }
        
        double totalWeight = requiredTypes.stream()
                .mapToInt(DocumentType::getWeight)
                .sum();
        
        if (totalWeight == 0) {
            return 100.0;
        }
        
        double earnedWeight = 0;
        
        // Since test is passing DocumentType instead of VendorDocument,
        // we'll assume all required documents are present
        for (DocumentType requiredType : requiredTypes) {
            // Check if this document type is in the "vendorDocuments" list
            boolean hasDocument = vendorDocuments.stream()
                    .anyMatch(doc -> doc.getId().equals(requiredType.getId()));
            
            if (hasDocument) {
                earnedWeight += requiredType.getWeight();
            }
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