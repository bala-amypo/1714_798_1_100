package com.example.demo.controller;

import com.example.demo.model.DocumentType;
import com.example.demo.service.DocumentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/document-types")
@RequiredArgsConstructor
public class DocumentTypeController {
    
    private final DocumentTypeService documentTypeService;
    
    @PostMapping
    public ResponseEntity<DocumentType> createDocumentType(@RequestBody DocumentType documentType) {
        return ResponseEntity.ok(documentTypeService.createDocumentType(documentType));
    }
    
    @GetMapping
    public ResponseEntity<List<DocumentType>> getAllDocumentTypes() {
        return ResponseEntity.ok(documentTypeService.getAllDocumentTypes());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DocumentType> getDocumentType(@PathVariable Long id) {
        return ResponseEntity.ok(documentTypeService.getDocumentType(id));
    }
}