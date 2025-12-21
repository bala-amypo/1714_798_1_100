package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.VendorDocument;
import com.example.demo.service.VendorDocumentService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vendor-documents")
public class VendorDocumentController {

    @Autowired
    VendorDocumentService vendorDocumentService;

    @PostMapping
    public VendorDocument uploadDocument(
            @RequestParam Long vendorId,
            @RequestParam Long typeId,
            @RequestBody VendorDocument document) {
        return vendorDocumentService.uploadDocument(vendorId, typeId, document);
    }

    @GetMapping
    public List<VendorDocument> getAllDocuments() {
        return vendorDocumentService.getAllDocuments();
    }

    @GetMapping("/vendor/{vendorId}")
    public List<VendorDocument> getDocumentsForVendor(@PathVariable Long vendorId) {
        return vendorDocumentService.getDocumentsForVendor(vendorId);
    }

    @GetMapping("/{id}")
    public Optional<VendorDocument> getDocument(@PathVariable Long id) {
        return vendorDocumentService.getDocumentById(id);
    }

    @PutMapping("/{id}")
    public String updateDocument(
            @PathVariable Long id,
            @RequestParam(required = false) Long vendorId,
            @RequestParam(required = false) Long typeId,
            @RequestBody VendorDocument newDocument) {
        Optional<VendorDocument> document = vendorDocumentService.getDocumentById(id);
        if (document.isPresent()) {
            newDocument.setId(id);
            vendorDocumentService.updateDocument(id, vendorId, typeId, newDocument);
            return "Document Updated Successfully";
        }
        return "Document not found";
    }

    @DeleteMapping("/{id}")
    public String deleteDocument(@PathVariable Long id) {
        Optional<VendorDocument> document = vendorDocumentService.getDocumentById(id);
        if (document.isPresent()) {
            vendorDocumentService.deleteDocument(id);
            return "Document Deleted Successfully";
        }
        return "Document not found";
    }
}