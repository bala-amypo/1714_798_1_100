// src/main/java/com/example/demo/controller/DocumentTypeController.java
package com.example.demo.controller;

import com.example.demo.dto.DocumentTypeRequest;
import com.example.demo.model.DocumentType;
import com.example.demo.service.DocumentTypeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/document-types")
public class DocumentTypeController {
    
    private final DocumentTypeService documentTypeService;
    
    public DocumentTypeController(DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }
    
    @PostMapping
    public DocumentType createDocumentType(@Valid @RequestBody DocumentTypeRequest request) {
        DocumentType documentType = new DocumentType();
        documentType.setTypeName(request.getTypeName());
        documentType.setDescription(request.getDescription());
        documentType.setRequired(request.getRequired());
        documentType.setWeight(request.getWeight());
        
        return documentTypeService.createDocumentType(documentType);
    }
    
    @GetMapping
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeService.getAllDocumentTypes();
    }
    
    @GetMapping("/{id}")
    public DocumentType getDocumentType(@PathVariable Long id) {
        return documentTypeService.getDocumentType(id);
    }
}