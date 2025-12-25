package com.example.demo.controller;

import com.example.demo.model.VendorDocument;
import com.example.demo.service.VendorDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-documents")
@RequiredArgsConstructor
public class VendorDocumentController {
    
    private final VendorDocumentService vendorDocumentService;
    
    @PostMapping
    public ResponseEntity<VendorDocument> uploadDocument(
            @RequestParam Long vendorId,
            @RequestParam Long typeId,
            @RequestBody VendorDocument document) {
        return ResponseEntity.ok(vendorDocumentService.uploadDocument(vendorId, typeId, document));
    }
    
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<VendorDocument>> getDocumentsForVendor(@PathVariable Long vendorId) {
        return ResponseEntity.ok(vendorDocumentService.getDocumentsForVendor(vendorId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VendorDocument> getDocument(@PathVariable Long id) {
        return ResponseEntity.ok(vendorDocumentService.getDocument(id));
    }
}