package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.DocumentType;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class DocumentTypeServiceImpl implements DocumentTypeService {
    
    @Autowired
    private DocumentTypeRepository documentTypeRepository;
    
    @Override
    public DocumentType createDocumentType(DocumentType type) {
        if (documentTypeRepository.existsByTypeName(type.getTypeName())) {
            throw new ValidationException("Document type name already exists: " + type.getTypeName());
        }
        return documentTypeRepository.save(type);
    }
    
    @Override
    public DocumentType getDocumentType(Long id) {
        return documentTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document type not found with id: " + id));
    }
    
    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeRepository.findAll();
    }
    
    @Override
    public void deleteDocumentType(Long id) {
        if (!documentTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Document type not found with id: " + id);
        }
        documentTypeRepository.deleteById(id);
    }
}