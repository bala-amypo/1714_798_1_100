package com.example.demo.service;

import com.example.demo.model.ComplianceScore;
import java.util.List;
import java.util.Optional;

public interface ComplianceScoreService {
    ComplianceScore evaluateVendor(Long vendorId);
    ComplianceScore getScore(Long vendorId);
    List<ComplianceScore> getAllScores();
    void deleteScore(Long id);
}