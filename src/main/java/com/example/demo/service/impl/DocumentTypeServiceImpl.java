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
    
    private final DocumentTypeRepository documentTypeRepository;
    
    public DocumentTypeServiceImpl(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }
    
    @Override
    public DocumentType createDocumentType(DocumentType documentType) {
        if (documentType.getWeight() <= 0) {
            throw new ValidationException("Weight must be greater than 0");
        }
        
        if (documentTypeRepository.existsByTypeName(documentType.getTypeName())) {
            throw new ValidationException("Document Type already exists");
        }
        return documentTypeRepository.save(documentType);
    }
    
    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeRepository.findAll();
    }
    
    @Override
    public DocumentType getDocumentType(Long id) {
        return documentTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document Type not found"));
    }
}