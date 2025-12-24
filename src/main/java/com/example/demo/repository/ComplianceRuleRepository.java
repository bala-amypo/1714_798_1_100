package com.example.demo.repository;

import com.example.demo.model.ComplianceRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComplianceRuleRepository extends JpaRepository<ComplianceRule, Long> {
    List<ComplianceRule> findByIsActiveTrue();
    List<ComplianceRule> findByMatchType(String matchType);
}