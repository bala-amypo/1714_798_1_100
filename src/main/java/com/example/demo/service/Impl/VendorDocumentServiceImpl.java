package com.example.demo.service;

import com.example.demo.model.DocumentType;
import com.example.demo.model.Vendor;
import com.example.demo.model.VendorDocument;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.repository.VendorDocumentRepository;
import com.example.demo.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class VendorDocumentServiceImpl implements VendorDocumentService {
    
    private final VendorDocumentRepository vendorDocumentRepository;
    private final VendorRepository vendorRepository;
    private final DocumentTypeRepository documentTypeRepository;
    
    @Autowired
    public VendorDocumentServiceImpl(
            VendorDocumentRepository vendorDocumentRepository,
            VendorRepository vendorRepository,
            DocumentTypeRepository documentTypeRepository) {
        this.vendorDocumentRepository = vendorDocumentRepository;
        this.vendorRepository = vendorRepository;
        this.documentTypeRepository = documentTypeRepository;
    }
    
    @Override
    public VendorDocument uploadDocument(Long vendorId, Long typeId, VendorDocument document) {
        // Load vendor and type
        Vendor vendor = vendorRepository.findById(vendorId)
            .orElseThrow(() -> new RuntimeException("Vendor not found"));
        
        DocumentType documentType = documentTypeRepository.findById(typeId)
            .orElseThrow(() -> new RuntimeException("Document type not found"));
        
        // Require fileUrl
        if (document.getFileUrl() == null || document.getFileUrl().trim().isEmpty()) {
            throw new RuntimeException("File URL is required");
        }
        
        // Check expiry date
        if (document.getExpiryDate() != null && document.getExpiryDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Expiry date cannot be in the past");
        }
        
        // Set relationships
        document.setVendor(vendor);
        document.setDocumentType(documentType);
        
        // Set isValid based on expiry
        if (document.getExpiryDate() != null) {
            document.setIsValid(document.getExpiryDate().isAfter(LocalDate.now()));
        }
        
        return vendorDocumentRepository.save(document);
    }
    
    @Override
    public List<VendorDocument> getDocumentsForVendor(Long vendorId) {
        // Verify vendor exists
        vendorRepository.findById(vendorId)
            .orElseThrow(() -> new RuntimeException("Vendor not found"));
        
        return vendorDocumentRepository.findByVendorId(vendorId);
    }
    
    @Override
    public VendorDocument getDocument(Long id) {
        return vendorDocumentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vendor document not found"));
    }
}