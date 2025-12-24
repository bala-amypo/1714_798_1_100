package com.example.demo.service.impl;

import com.example.demo.model.DocumentType;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.DocumentTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DocumentTypeServiceImpl implements DocumentTypeService {
    
    private final DocumentTypeRepository documentTypeRepository;
    
    public DocumentTypeServiceImpl(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }
    
    @Override
    public DocumentType createDocumentType(DocumentType documentType) {
        return documentTypeRepository.save(documentType);
    }
    
    @Override
    public DocumentType getDocumentType(Long id) {
        return documentTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DocumentType not found with id: " + id));
    }
    
    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeRepository.findAll();
    }
    
    @Override
    public List<DocumentType> getRequiredDocumentTypes() {
        return documentTypeRepository.findByRequiredTrue();
    }
    
    @Override
    public DocumentType updateDocumentType(Long id, DocumentType documentTypeDetails) {
        DocumentType documentType = getDocumentType(id);
        documentType.setTypeName(documentTypeDetails.getTypeName());
        documentType.setWeight(documentTypeDetails.getWeight());
        documentType.setRequired(documentTypeDetails.getRequired());
        documentType.setDescription(documentTypeDetails.getDescription());
        return documentTypeRepository.save(documentType);
    }
    
    @Override
    public void deleteDocumentType(Long id) {
        DocumentType documentType = getDocumentType(id);
        documentTypeRepository.delete(documentType);
    }
}