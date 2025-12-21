package com.example.demo.service.impl;

import com.example.demo.model.DocumentType;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class DocumentTypeServiceImpl implements DocumentTypeService {
    
    @Autowired
    private DocumentTypeRepository documentTypeRepository;
    
    @Override
    public DocumentType createDocumentType(DocumentType type) {
        if (documentTypeRepository.existsByTypeName(type.getTypeName())) {
            throw new IllegalArgumentException("Document type name already exists: " + type.getTypeName());
        }
        return documentTypeRepository.save(type);
    }
    
    @Override
    public DocumentType getDocumentType(Long id) {
        return documentTypeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Document type not found with id: " + id));
    }
    
    @Override
    public DocumentType getDocumentTypeById(Long id) {
        return getDocumentType(id);
    }
    
    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeRepository.findAll();
    }
    
    @Override
    public DocumentType updateDocumentType(DocumentType type) {
        DocumentType existingType = getDocumentType(type.getId());
        
        // Update fields if provided
        if (type.getTypeName() != null && !type.getTypeName().equals(existingType.getTypeName())) {
            // Check if new type name already exists (excluding current type)
            if (documentTypeRepository.existsByTypeName(type.getTypeName())) {
                throw new IllegalArgumentException("Document type name already exists: " + type.getTypeName());
            }
            existingType.setTypeName(type.getTypeName());
        }
        
        if (type.getDescription() != null) {
            existingType.setDescription(type.getDescription());
        }
        
        if (type.getRequired() != null) {
            existingType.setRequired(type.getRequired());
        }
        
        if (type.getValidityPeriod() != null) {
            existingType.setValidityPeriod(type.getValidityPeriod());
        }
        
        return documentTypeRepository.save(existingType);
    }
    
    @Override
    public void deleteDocumentType(Long id) {
        if (!documentTypeRepository.existsById(id)) {
            throw new NoSuchElementException("Document type not found with id: " + id);
        }
        documentTypeRepository.deleteById(id);
    }
}