package com.example.demo.service;

import com.example.demo.model.DocumentType;
import java.util.List;

public interface DocumentTypeService {
    DocumentType createDocumentType(DocumentType type);
    DocumentType getDocumentType(Long id);
    DocumentType getDocumentTypeById(Long id); // Add this
    List<DocumentType> getAllDocumentTypes();
    DocumentType updateDocumentType(DocumentType type); // Add this
    void deleteDocumentType(Long id);
}