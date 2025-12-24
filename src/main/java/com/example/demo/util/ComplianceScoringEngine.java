package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ComplianceScoringEngine {
    
    public double calculateScore(List<DocumentType> requiredTypes, List<VendorDocument> vendorDocuments) {
        if (requiredTypes.isEmpty()) {
            return 100.0; // No required documents means perfect score
        }
        
        double totalWeight = requiredTypes.stream()
                .mapToInt(DocumentType::getWeight)
                .sum();
        
        if (totalWeight == 0) {
            totalWeight = requiredTypes.size(); // Default weight of 1 per type
        }
        
        double earnedWeight = 0.0;
        
        for (DocumentType requiredType : requiredTypes) {
            boolean hasValidDocument = vendorDocuments.stream()
                    .anyMatch(doc -> 
                        doc.getDocumentType().getId().equals(requiredType.getId()) &&
                        doc.getIsValid() &&
                        (doc.getExpiryDate() == null || !doc.getExpiryDate().isBefore(LocalDate.now()))
                    );
            
            if (hasValidDocument) {
                earnedWeight += requiredType.getWeight() > 0 ? requiredType.getWeight() : 1;
            }
        }
        
        if (totalWeight == 0) {
            return 0.0;
        }
        
        double percentage = (earnedWeight / totalWeight) * 100;
        return Math.min(100.0, Math.max(0.0, percentage));
    }
    
    public String deriveRating(double score) {
        if (score >= 90) {
            return "EXCELLENT";
        } else if (score >= 70) {
            return "GOOD";
        } else if (score >= 50) {
            return "POOR";
        } else {
            return "NONCOMPLIANT";
        }
    }
}