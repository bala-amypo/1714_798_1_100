package com.example.demo.service;

import com.example.demo.model.DocumentType;
import java.util.List;

public interface DocumentTypeService {
    DocumentType createDocumentType(DocumentType documentType);
    DocumentType getDocumentType(Long id);
    List<DocumentType> getAllDocumentTypes();
    List<DocumentType> getRequiredDocumentTypes();
    DocumentType updateDocumentType(Long id, DocumentType documentTypeDetails);
    void deleteDocumentType(Long id);
}