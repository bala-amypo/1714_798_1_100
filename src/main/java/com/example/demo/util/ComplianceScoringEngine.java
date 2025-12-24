package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;
import java.util.List;

public class ComplianceScoringEngine {
    
    public double calculateScore(List<DocumentType> requiredTypes, List<VendorDocument> vendorDocuments) {
        if (requiredTypes.isEmpty()) {
            return 100.0;
        }
        
        double totalWeight = 0.0;
        double achievedWeight = 0.0;
        
        for (DocumentType docType : requiredTypes) {
            totalWeight += docType.getWeight();
            
            boolean hasValidDocument = vendorDocuments.stream()
                    .anyMatch(doc -> 
                        doc.getDocumentType().getId().equals(docType.getId()) && 
                        Boolean.TRUE.equals(doc.getIsValid())
                    );
            
            if (hasValidDocument) {
                achievedWeight += docType.getWeight();
            }
        }
        
        if (totalWeight == 0) {
            return 100.0;
        }
        
        return (achievedWeight / totalWeight) * 100.0;
    }
    
    public String deriveRating(double score) {
        if (score >= 90.0) {
            return "EXCELLENT";
        } else if (score >= 70.0) {
            return "GOOD";
        } else if (score >= 50.0) {
            return "POOR";
        } else {
            return "NON_COMPLIANT";
        }
    }
}