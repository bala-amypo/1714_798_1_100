package com.example.demo.service.impl;

import com.example.demo.model.DocumentType;
import com.example.demo.repository.DocumentTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentTypeServiceImpl {
    
    private final DocumentTypeRepository documentTypeRepository;
    
    public DocumentTypeServiceImpl(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }
    
    // Add methods as needed
}