package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VendorDocumentServiceImpl {
    
    private final VendorDocumentRepository vendorDocumentRepository;
    private final VendorRepository vendorRepository;
    private final DocumentTypeRepository documentTypeRepository;
    
    public VendorDocumentServiceImpl(
            VendorDocumentRepository vendorDocumentRepository,
            VendorRepository vendorRepository,
            DocumentTypeRepository documentTypeRepository) {
        this.vendorDocumentRepository = vendorDocumentRepository;
        this.vendorRepository = vendorRepository;
        this.documentTypeRepository = documentTypeRepository;
    }
    
    public VendorDocument uploadDocument(Long vendorId, Long documentTypeId, VendorDocument document) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
        
        DocumentType documentType = documentTypeRepository.findById(documentTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("DocumentType not found"));
        
        if (document.getExpiryDate() != null && document.getExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expiry date cannot be in the past");
        }
        
        document.setVendor(vendor);
        document.setDocumentType(documentType);
        
        return vendorDocumentRepository.save(document);
    }
    
    public VendorDocument getDocument(Long id) {
        return vendorDocumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VendorDocument not found with id: " + id));
    }
}