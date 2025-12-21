package com.example.demo.service;
import com.example.demo.model.DocumentType;
import java.util.List;

public interface DocumentTypeService {
    DocumentType createDocumentType(DocumentType type);
    List<DocumentType> getAllDocumentTypes();
    DocumentType getDocumentType(Long id);
    DocumentType updateDocumentType(Long id, DocumentType type);
    void deleteDocumentType(Long id);
    List<DocumentType> getRequiredDocumentTypes();
}