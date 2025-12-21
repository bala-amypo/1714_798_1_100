package com.example.demo.controller;
import com.example.demo.model.DocumentType;
import com.example.demo.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/document-types")
public class DocumentTypeController {
    
    private final DocumentTypeService documentTypeService;
    
    @Autowired
    public DocumentTypeController(DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }
    
    @PostMapping
    public ResponseEntity<DocumentType> createDocumentType(@RequestBody DocumentType documentType) {
        DocumentType createdType = documentTypeService.createDocumentType(documentType);
        return new ResponseEntity<>(createdType, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<DocumentType>> getAllDocumentTypes() {
        List<DocumentType> types = documentTypeService.getAllDocumentTypes();
        return ResponseEntity.ok(types);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DocumentType> getDocumentTypeById(@PathVariable Long id) {
        DocumentType type = documentTypeService.getDocumentType(id);
        return ResponseEntity.ok(type);
    }
    
    @GetMapping("/required")
    public ResponseEntity<List<DocumentType>> getRequiredDocumentTypes() {
        List<DocumentType> types = documentTypeService.getRequiredDocumentTypes();
        return ResponseEntity.ok(types);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DocumentType> updateDocumentType(@PathVariable Long id, @RequestBody DocumentType documentType) {
        DocumentType updatedType = documentTypeService.updateDocumentType(id, documentType);
        return ResponseEntity.ok(updatedType);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentType(@PathVariable Long id) {
        documentTypeService.deleteDocumentType(id);
        return ResponseEntity.noContent().build();
    }
}