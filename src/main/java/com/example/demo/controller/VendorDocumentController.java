// src/main/java/com/example/demo/controller/VendorDocumentController.java
package com.example.demo.controller;

import com.example.demo.model.VendorDocument;
import com.example.demo.service.VendorDocumentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vendor-documents")
public class VendorDocumentController {
    
    private final VendorDocumentService vendorDocumentService;
    
    public VendorDocumentController(VendorDocumentService vendorDocumentService) {
        this.vendorDocumentService = vendorDocumentService;
    }
    
    @PostMapping
    public VendorDocument uploadDocument(
            @RequestParam Long vendorId,
            @RequestParam Long typeId,
            @Valid @RequestBody VendorDocument document) {
        return vendorDocumentService.uploadDocument(vendorId, typeId, document);
    }
    
    @GetMapping("/vendor/{vendorId}")
    public List<VendorDocument> getDocumentsForVendor(@PathVariable Long vendorId) {
        return vendorDocumentService.getDocumentsForVendor(vendorId);
    }
    
    @GetMapping("/{id}")
    public VendorDocument getDocument(@PathVariable Long id) {
        return vendorDocumentService.getDocument(id);
    }
}