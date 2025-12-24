package com.example.demo.service.impl;

import com.example.demo.model.DocumentType;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DocumentTypeServiceImpl {
    
    private final DocumentTypeRepository documentTypeRepository;
    
    public DocumentTypeServiceImpl(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }
    
    public DocumentType createDocumentType(DocumentType documentType) {
        return documentTypeRepository.save(documentType);
    }
    
    public DocumentType getDocumentType(Long id) {
        return documentTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DocumentType not found with id: " + id));
    }
    
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeRepository.findAll();
    }
    
    public List<DocumentType> getRequiredDocumentTypes() {
        return documentTypeRepository.findByRequiredTrue();
    }
    
    public DocumentType updateDocumentType(Long id, DocumentType documentTypeDetails) {
        DocumentType documentType = getDocumentType(id);
        documentType.setTypeName(documentTypeDetails.getTypeName());
        documentType.setWeight(documentTypeDetails.getWeight());
        documentType.setRequired(documentTypeDetails.getRequired());
        documentType.setDescription(documentTypeDetails.getDescription());
        return documentTypeRepository.save(documentType);
    }
    
    public void deleteDocumentType(Long id) {
        DocumentType documentType = getDocumentType(id);
        documentTypeRepository.delete(documentType);
    }
}