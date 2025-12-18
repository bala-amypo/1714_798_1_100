package com.example.demo.controller;

import com.example.demo.model.VendorDocument;
import com.example.demo.service.VendorDocumentService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-documents")
@Tag(name = "Vendor Documents")
public class VendorDocumentController {

    private final VendorDocumentService service;

    public VendorDocumentController(VendorDocumentService service) {
        this.service = service;
    }

    @PostMapping("/{vendorId}/{typeId}")
    public VendorDocument upload(
            @PathVariable Long vendorId,
            @PathVariable Long typeId,
            @RequestBody VendorDocument doc) {

        return service.uploadDocument(vendorId, typeId, doc);
    }

    @GetMapping("/vendor/{vendorId}")
    public List<VendorDocument> getVendorDocs(@PathVariable Long vendorId) {
        return service.getDocumentsForVendor(vendorId);
    }

    @GetMapping("/{id}")
    public VendorDocument get(@PathVariable Long id) {
        return service.getDocument(id);
    }
}
