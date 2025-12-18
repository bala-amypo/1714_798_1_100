package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.ComplianceScoreService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplianceScoreServiceImpl implements ComplianceScoreService {

    private final ComplianceScoreRepository scoreRepo;
    private final VendorRepository vendorRepo;
    private final VendorDocumentRepository docRepo;
    private final ComplianceRuleRepository ruleRepo;

    public ComplianceScoreServiceImpl(ComplianceScoreRepository scoreRepo,
                                      VendorRepository vendorRepo,
                                      VendorDocumentRepository docRepo,
                                      ComplianceRuleRepository ruleRepo) {
        this.scoreRepo = scoreRepo;
        this.vendorRepo = vendorRepo;
        this.docRepo = docRepo;
        this.ruleRepo = ruleRepo;
    }

    @Override
    public ComplianceScore evaluateVendor(Long vendorId) {

        Vendor vendor = vendorRepo.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        List<VendorDocument> docs = docRepo.findByVendor_Id(vendorId);

        double score = docs.stream()
                .filter(VendorDocument::getIsValid)
                .mapToInt(d -> d.getDocumentType().getWeight())
                .sum();

        if (score < 0) {
            throw new ValidationException("Compliance score cannot be negative");
        }

        String rating =
                score >= 80 ? "EXCELLENT" :
                score >= 60 ? "GOOD" :
                score >= 40 ? "POOR" : "NON_COMPLIANT";

        ComplianceScore cs = scoreRepo.findByVendor_Id(vendorId)
                .orElse(new ComplianceScore());

        cs.setVendor(vendor);
        cs.setScoreValue(score);
        cs.setRating(rating);
        cs.setLastEvaluated(LocalDateTime.now());

        return scoreRepo.save(cs);
    }

    @Override
    public ComplianceScore getScore(Long vendorId) {
        return scoreRepo.findByVendor_Id(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found"));
    }

    @Override
    public List<ComplianceScore> getAllScores() {
        return scoreRepo.findAll();
    }
}
