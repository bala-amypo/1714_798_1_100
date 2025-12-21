package com.example.demo.service;

import com.example.demo.model.ComplianceScore;
import java.util.List;

public interface ComplianceScoreService {
    ComplianceScore evaluateVendor(Long vendorId);
    ComplianceScore getScore(Long vendorId);
    ComplianceScore getScoreByVendorId(Long vendorId); // Add this
    ComplianceScore getScoreById(Long id); // Add this
    List<ComplianceScore> getAllScores();
    void deleteScore(Long id);
}