package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ComplianceScoringEngine {
    
    public double calculateScore(List<DocumentType> requiredTypes, List<VendorDocument> vendorDocuments) {
        if (requiredTypes.isEmpty()) {
            return 100.0;
        }
        
        // Group documents by document type for faster lookup
        Map<Long, VendorDocument> validDocumentsByType = vendorDocuments.stream()
                .filter(doc -> Boolean.TRUE.equals(doc.getIsValid()))
                .collect(Collectors.toMap(
                    doc -> doc.getDocumentType().getId(),
                    doc -> doc,
                    (existing, replacement) -> existing
                ));
        
        double totalWeight = 0.0;
        double achievedWeight = 0.0;
        
        for (DocumentType docType : requiredTypes) {
            totalWeight += docType.getWeight();
            
            if (validDocumentsByType.containsKey(docType.getId())) {
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
    
    public Map<String, Object> getScoreDetails(List<DocumentType> requiredTypes, List<VendorDocument> vendorDocuments) {
        double score = calculateScore(requiredTypes, vendorDocuments);
        String rating = deriveRating(score);
        
        long totalRequired = requiredTypes.size();
        long totalCompliant = vendorDocuments.stream()
                .filter(doc -> Boolean.TRUE.equals(doc.getIsValid()))
                .filter(doc -> requiredTypes.stream()
                        .anyMatch(type -> type.getId().equals(doc.getDocumentType().getId())))
                .count();
        
        return Map.of(
            "score", score,
            "rating", rating,
            "totalRequired", totalRequired,
            "totalCompliant", totalCompliant,
            "compliancePercentage", score
        );
    }
}