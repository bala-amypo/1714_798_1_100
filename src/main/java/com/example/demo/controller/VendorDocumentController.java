package com.example.demo.controller;

import com.example.demo.dto.VendorDocumentDTO;
import com.example.demo.model.VendorDocument;
import com.example.demo.service.VendorDocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vendor-documents")
public class VendorDocumentController {
    
    @Autowired
    private VendorDocumentService vendorDocumentService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping
    public ResponseEntity<VendorDocumentDTO> uploadDocument(
            @RequestParam Long vendorId,
            @RequestParam Long typeId,
            @RequestBody VendorDocument document) {
        
        VendorDocument uploadedDoc = vendorDocumentService.uploadDocument(vendorId, typeId, document);
        return ResponseEntity.ok(convertToDto(uploadedDoc));
    }
    
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<VendorDocumentDTO>> getDocumentsForVendor(@PathVariable Long vendorId) {
        List<VendorDocument> documents = vendorDocumentService.getDocumentsForVendor(vendorId);
        List<VendorDocumentDTO> dtos = documents.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VendorDocumentDTO> getDocument(@PathVariable Long id) {
        VendorDocument document = vendorDocumentService.getDocument(id);
        return ResponseEntity.ok(convertToDto(document));
    }
    
    private VendorDocumentDTO convertToDto(VendorDocument document) {
        VendorDocumentDTO dto = modelMapper.map(document, VendorDocumentDTO.class);
        dto.setVendorId(document.getVendor().getId());
        dto.setDocumentTypeId(document.getDocumentType().getId());
        dto.setVendorName(document.getVendor().getVendorName());
        dto.setDocumentTypeName(document.getDocumentType().getTypeName());
        return dto;
    }
}