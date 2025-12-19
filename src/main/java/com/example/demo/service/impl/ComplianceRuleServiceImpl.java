package com.example.demo.service.impl;

import com.example.demo.model.ComplianceRule;
import com.example.demo.repository.ComplianceRuleRepository;
import com.example.demo.service.ComplianceRuleService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplianceRuleServiceImpl implements ComplianceRuleService {

    private final ComplianceRuleRepository repository;

    public ComplianceRuleServiceImpl(ComplianceRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public ComplianceRule save(ComplianceRule rule) {
        return repository.save(rule);
    }

    @Override
    public List<ComplianceRule> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ComplianceRule> findById(Long id) {
        return repository.findById(id);
    }
}
