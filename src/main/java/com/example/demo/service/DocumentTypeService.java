package com.example.demo.service;

import com.example.demo.model.DocumentType;
import java.util.List;
import java.util.Optional;

public interface DocumentTypeService {
    DocumentType createDocumentType(DocumentType type);
    DocumentType getDocumentType(Long id);
    List<DocumentType> getAllDocumentTypes();
    void deleteDocumentType(Long id);
}