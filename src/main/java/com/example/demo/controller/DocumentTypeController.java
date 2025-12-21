package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.DocumentType;
import com.example.demo.service.DocumentTypeService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/document-types")
public class DocumentTypeController {

    @Autowired
    DocumentTypeService documentTypeService;

    @PostMapping
    public DocumentType createDocumentType(@RequestBody DocumentType documentType) {
        return documentTypeService.createDocumentType(documentType);
    }

    @GetMapping
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeService.getAllDocumentTypes();
    }

    @GetMapping("/{id}")
    public Optional<DocumentType> getDocumentType(@PathVariable Long id) {
        return documentTypeService.getDocumentTypeById(id);
    }

    @PutMapping("/{id}")
    public String updateDocumentType(@PathVariable Long id, @RequestBody DocumentType newDocumentType) {
        Optional<DocumentType> documentType = documentTypeService.getDocumentTypeById(id);
        if (documentType.isPresent()) {
            newDocumentType.setId(id);
            documentTypeService.updateDocumentType(newDocumentType);
            return "Document Type Updated Successfully";
        }
        return "Document Type not found";
    }

    @DeleteMapping("/{id}")
    public String deleteDocumentType(@PathVariable Long id) {
        Optional<DocumentType> documentType = documentTypeService.getDocumentTypeById(id);
        if (documentType.isPresent()) {
            documentTypeService.deleteDocumentType(id);
            return "Document Type Deleted Successfully";
        }
        return "Document Type not found";
    }
}
