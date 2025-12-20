package com.example.demo.util;

import com.example.demo.model.DocumentType;
import com.example.demo.model.VendorDocument;
import java.time.LocalDate;
import java.util.List;

public class ComplianceScoringEngine {
    
    public static double calculateScore(List<DocumentType> requiredTypes, List<VendorDocument> vendorDocuments) {
        if (requiredTypes.isEmpty()) {
            return 100.0; // No requirements means perfect score
        }
        
        double totalPossibleWeight = 0.0;
        double actualWeight = 0.0;
        
        for (DocumentType requiredType : requiredTypes) {
            int weight = requiredType.getWeight();
            totalPossibleWeight += weight;
            
            // Find matching document for this type
            VendorDocument matchingDoc = vendorDocuments.stream()
                    .filter(doc -> doc.getDocumentType().getId().equals(requiredType.getId()))
                    .findFirst()
                    .orElse(null);
            
            if (matchingDoc != null && matchingDoc.getIsValid()) {
                actualWeight += weight; // Full weight for valid document
            } else if (matchingDoc != null && !matchingDoc.getIsValid()) {
                actualWeight += weight * 0.5; // Half weight for expired document
            }
            // No document = 0 weight
        }
        
        if (totalPossibleWeight == 0) {
            return 0.0;
        }
        
        return (actualWeight / totalPossibleWeight) * 100.0;
    }
    
    public static String deriveRating(double score) {
        if (score >= 90) {
            return "EXCELLENT";
        } else if (score >= 75) {
            return "GOOD";
        } else if (score >= 60) {
            return "FAIR";
        } else if (score >= 40) {
            return "POOR";
        } else {
            return "NONCOMPLIANT";
        }
    }
    
    public static boolean isDocumentExpired(VendorDocument document) {
        if (document.getExpiryDate() == null) {
            return false;
        }
        return document.getExpiryDate().isBefore(LocalDate.now());
    }
}