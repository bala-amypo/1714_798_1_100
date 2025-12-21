package com.example.demo.service;

import com.example.demo.model.DocumentType;
import com.example.demo.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class DocumentTypeServiceImpl implements DocumentTypeService {
    
    private final DocumentTypeRepository documentTypeRepository;
    
    @Autowired
    public DocumentTypeServiceImpl(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }
    
    @Override
    public DocumentType createDocumentType(DocumentType type) {
        // Enforce unique typeName
        if (documentTypeRepository.existsByTypeName(type.getTypeName())) {
            throw new RuntimeException("Duplicate document type name");
        }
        
        // Ensure weight is not negative
        if (type.getWeight() < 0) {
            type.setWeight(0);
        }
        
        return documentTypeRepository.save(type);
    }
    
    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeRepository.findAll();
    }
    
    @Override
    public DocumentType getDocumentType(Long id) {
        return documentTypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Document type not found"));
    }
}