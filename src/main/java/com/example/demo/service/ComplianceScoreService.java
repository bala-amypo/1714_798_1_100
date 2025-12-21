package com.example.demo.service;

import com.example.demo.model.ComplianceScore;
import java.util.List;

public interface ComplianceScoreService {
    ComplianceScore evaluateVendor(Long vendorId);
    ComplianceScore getScore(Long vendorId);
    List<ComplianceScore> getAllScores();
    ComplianceScore updateScore(Long vendorId, ComplianceScore score);
    void deleteScore(Long vendorId);
    
    // Remove these methods if they're not in the repository:
    // List<ComplianceScore> getScoresByRating(String rating);
    // List<ComplianceScore> getScoresByScoreRange(Double minScore, Double maxScore);
    // List<ComplianceScore> getAllScoresOrdered();
}