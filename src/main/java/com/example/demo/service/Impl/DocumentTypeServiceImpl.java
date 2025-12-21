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
        if (documentTypeRepository.existsByTypeName(type.getTypeName())) {
            throw new RuntimeException("Duplicate document type name: " + type.getTypeName());
        }
        
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
            .orElseThrow(() -> new RuntimeException("Document type not found with ID: " + id));
    }
    
    @Override
    public DocumentType updateDocumentType(Long id, DocumentType type) {
        DocumentType existingType = getDocumentType(id);
        
        if (!existingType.getTypeName().equals(type.getTypeName()) && 
            documentTypeRepository.existsByTypeName(type.getTypeName())) {
            throw new RuntimeException("Duplicate document type name: " + type.getTypeName());
        }
        
        existingType.setTypeName(type.getTypeName());
        existingType.setDescription(type.getDescription());
        existingType.setRequired(type.getRequired());
        existingType.setWeight(type.getWeight());
        
        if (existingType.getWeight() < 0) {
            existingType.setWeight(0);
        }
        
        return documentTypeRepository.save(existingType);
    }
    
    @Override
    public void deleteDocumentType(Long id) {
        DocumentType documentType = getDocumentType(id);
        documentTypeRepository.delete(documentType);
    }
    
    @Override
    public List<DocumentType> getRequiredDocumentTypes() {
        return documentTypeRepository.findByRequiredTrue();
    }
}