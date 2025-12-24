package com.example.demo.controller;

import com.example.demo.dto.VendorDocumentDTO;
import com.example.demo.model.VendorDocument;
import com.example.demo.service.VendorDocumentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vendor-documents")
public class VendorDocumentController {
    
    private final VendorDocumentService vendorDocumentService;
    
    public VendorDocumentController(VendorDocumentService vendorDocumentService) {
        this.vendorDocumentService = vendorDocumentService;
    }
    
    @PostMapping
    public ResponseEntity<VendorDocumentDTO> uploadDocument(@Valid @RequestBody VendorDocumentDTO documentDTO) {
        VendorDocument document = new VendorDocument();
        document.setFileUrl(documentDTO.getFileUrl());
        document.setExpiryDate(documentDTO.getExpiryDate());
        
        VendorDocument savedDocument = vendorDocumentService.uploadDocument(
            documentDTO.getVendorId(),
            documentDTO.getDocumentTypeId(),
            document
        );
        
        return new ResponseEntity<>(convertToDTO(savedDocument), HttpStatus.CREATED);
    }
    
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<VendorDocumentDTO>> getDocumentsForVendor(@PathVariable Long vendorId) {
        List<VendorDocument> documents = vendorDocumentService.getDocumentsForVendor(vendorId);
        List<VendorDocumentDTO> dtos = documents.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VendorDocumentDTO> getDocument(@PathVariable Long id) {
        VendorDocument document = vendorDocumentService.getDocument(id);
        return ResponseEntity.ok(convertToDTO(document));
    }
    
    private VendorDocumentDTO convertToDTO(VendorDocument document) {
        VendorDocumentDTO dto = new VendorDocumentDTO();
        dto.setId(document.getId());
        dto.setVendorId(document.getVendor().getId());
        dto.setDocumentTypeId(document.getDocumentType().getId());
        dto.setFileUrl(document.getFileUrl());
        dto.setExpiryDate(document.getExpiryDate());
        dto.setIsValid(document.getIsValid());
        return dto;
    }
}