package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.DocumentType;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.service.DocumentTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository repository;

    public DocumentTypeServiceImpl(DocumentTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public DocumentType createDocumentType(DocumentType type) {
        if (repository.existsByTypeName(type.getTypeName())) {
            throw new ValidationException("Document Type already exists");
        }
        return repository.save(type);
    }

    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return repository.findAll();
    }

    @Override
    public DocumentType getDocumentType(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document type not found"));
    }
}
