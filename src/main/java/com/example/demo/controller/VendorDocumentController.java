package com.example.demo.controller;

import com.example.demo.model.VendorDocument;
import com.example.demo.service.VendorDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vendor-documents")
@Tag(name = "Vendor Documents", description = "Vendor document management endpoints")
public class VendorDocumentController {
    
    private final VendorDocumentService vendorDocumentService;
    
    public VendorDocumentController(VendorDocumentService vendorDocumentService) {
        this.vendorDocumentService = vendorDocumentService;
    }
    
    @PostMapping("/{vendorId}/{typeId}")
    @Operation(summary = "Upload a document for a vendor")
    public ResponseEntity<VendorDocument> uploadDocument(
            @PathVariable Long vendorId,
            @PathVariable Long typeId,
            @RequestBody VendorDocument document) {
        
        VendorDocument uploadedDoc = vendorDocumentService.uploadDocument(vendorId, typeId, document);
        return new ResponseEntity<>(uploadedDoc, HttpStatus.CREATED);
    }
    
    @GetMapping("/vendor/{vendorId}")
    @Operation(summary = "Get all documents for a vendor")
    public ResponseEntity<List<VendorDocument>> getDocumentsForVendor(@PathVariable Long vendorId) {
        List<VendorDocument> documents = vendorDocumentService.getDocumentsForVendor(vendorId);
        return ResponseEntity.ok(documents);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get document by ID")
    public ResponseEntity<VendorDocument> getDocument(@PathVariable Long id) {
        VendorDocument document = vendorDocumentService.getDocument(id);
        return ResponseEntity.ok(document);
    }
}