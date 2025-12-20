package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public class ComplianceScoringEngine {
    
    public Double calculateScore(List<VendorDocument> vendorDocuments, List<DocumentType> requiredTypes) {
        if (requiredTypes.isEmpty()) {
            return 100.0;
        }
        
        double totalWeight = requiredTypes.stream()
                .mapToInt(DocumentType::getWeight)
                .sum();
        
        if (totalWeight == 0) {
            totalWeight = requiredTypes.size();
        }
        
        double achievedWeight = 0.0;
        
        for (DocumentType requiredType : requiredTypes) {
            boolean hasValidDocument = vendorDocuments.stream()
                    .anyMatch(doc -> 
                        doc.getDocumentType().getId().equals(requiredType.getId()) &&
                        Boolean.TRUE.equals(doc.getIsValid()) &&
                        (doc.getExpiryDate() == null || !doc.getExpiryDate().isBefore(LocalDate.now()))
                    );
            
            if (hasValidDocument) {
                achievedWeight += requiredType.getWeight() > 0 ? requiredType.getWeight() : 1;
            }
        }
        
        double score = (achievedWeight / totalWeight) * 100;
        return Math.min(100.0, Math.max(0.0, score));
    }
    
    public String deriveRating(double score) {
        if (score >= 90) return "EXCELLENT";
        if (score >= 70) return "GOOD";
        if (score >= 40) return "POOR";
        return "NONCOMPLIANT";
    }
}