// src/main/java/com/example/demo/repository/ComplianceRuleRepository.java
package com.example.demo.repository;

import com.example.demo.model.ComplianceRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplianceRuleRepository extends JpaRepository<ComplianceRule, Long> {
}