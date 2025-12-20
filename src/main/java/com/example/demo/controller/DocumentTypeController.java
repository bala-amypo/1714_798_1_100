package com.example.demo.controller;

import com.example.demo.dto.DocumentTypeDTO;
import com.example.demo.model.DocumentType;
import com.example.demo.service.DocumentTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/document-types")
@PreAuthorize("hasRole('ADMIN')")
public class DocumentTypeController {
    
    @Autowired
    private DocumentTypeService documentTypeService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping
    public ResponseEntity<DocumentTypeDTO> createDocumentType(@RequestBody DocumentType documentType) {
        DocumentType createdType = documentTypeService.createDocumentType(documentType);
        return ResponseEntity.ok(modelMapper.map(createdType, DocumentTypeDTO.class));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeDTO> getDocumentType(@PathVariable Long id) {
        DocumentType documentType = documentTypeService.getDocumentType(id);
        return ResponseEntity.ok(modelMapper.map(documentType, DocumentTypeDTO.class));
    }
    
    @GetMapping
    public ResponseEntity<List<DocumentTypeDTO>> getAllDocumentTypes() {
        List<DocumentType> types = documentTypeService.getAllDocumentTypes();
        List<DocumentTypeDTO> dtos = types.stream()
                .map(type -> modelMapper.map(type, DocumentTypeDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}