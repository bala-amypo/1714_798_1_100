package com.example.demo.controller;

import com.example.demo.dto.DocumentTypeDTO;
import com.example.demo.model.DocumentType;
import com.example.demo.service.DocumentTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/document-types")
public class DocumentTypeController {
    
    private final DocumentTypeService documentTypeService;
    
    public DocumentTypeController(DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }
    
    @PostMapping
    public ResponseEntity<DocumentTypeDTO> createDocumentType(@Valid @RequestBody DocumentTypeDTO documentTypeDTO) {
        DocumentType documentType = new DocumentType();
        documentType.setTypeName(documentTypeDTO.getTypeName());
        documentType.setDescription(documentTypeDTO.getDescription());
        documentType.setRequired(documentTypeDTO.getRequired());
        documentType.setWeight(documentTypeDTO.getWeight());
        
        DocumentType savedType = documentTypeService.createDocumentType(documentType);
        return new ResponseEntity<>(convertToDTO(savedType), HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<DocumentTypeDTO>> getAllDocumentTypes() {
        List<DocumentType> types = documentTypeService.getAllDocumentTypes();
        List<DocumentTypeDTO> dtos = types.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeDTO> getDocumentType(@PathVariable Long id) {
        DocumentType type = documentTypeService.getDocumentType(id);
        return ResponseEntity.ok(convertToDTO(type));
    }
    
    private DocumentTypeDTO convertToDTO(DocumentType type) {
        DocumentTypeDTO dto = new DocumentTypeDTO();
        dto.setId(type.getId());
        dto.setTypeName(type.getTypeName());
        dto.setDescription(type.getDescription());
        dto.setRequired(type.getRequired());
        dto.setWeight(type.getWeight());
        return dto;
    }
}