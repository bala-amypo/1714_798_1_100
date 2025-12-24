package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.VendorDocumentService;
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
    
    public VendorDocumentServiceImpl(
            VendorDocumentRepository vendorDocumentRepository,
            VendorRepository vendorRepository,
            DocumentTypeRepository documentTypeRepository) {
        this.vendorDocumentRepository = vendorDocumentRepository;
        this.vendorRepository = vendorRepository;
        this.documentTypeRepository = documentTypeRepository;
    }
    
    @Override
    public VendorDocument uploadDocument(Long vendorId, Long documentTypeId, VendorDocument document) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + vendorId));
        
        DocumentType documentType = documentTypeRepository.findById(documentTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("DocumentType not found with id: " + documentTypeId));
        
        if (document.getExpiryDate() != null && document.getExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expiry date cannot be in the past");
        }
        
        document.setVendor(vendor);
        document.setDocumentType(documentType);
        
        return vendorDocumentRepository.save(document);
    }
    
    @Override
    public VendorDocument getDocument(Long id) {
        return vendorDocumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VendorDocument not found with id: " + id));
    }
    
    @Override
    public List<VendorDocument> getDocumentsByVendor(Long vendorId) {
        return vendorDocumentRepository.findByVendorId(vendorId);
    }
    
    @Override
    public List<VendorDocument> getExpiredDocuments() {
        return vendorDocumentRepository.findExpiredDocuments(LocalDate.now());
    }
    
    @Override
    public VendorDocument updateDocument(Long id, VendorDocument documentDetails) {
        VendorDocument document = getDocument(id);
        document.setFileUrl(documentDetails.getFileUrl());
        document.setFileName(documentDetails.getFileName());
        document.setExpiryDate(documentDetails.getExpiryDate());
        document.setIsValid(documentDetails.getIsValid());
        
        if (documentDetails.getExpiryDate() != null && 
            documentDetails.getExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expiry date cannot be in the past");
        }
        
        return vendorDocumentRepository.save(document);
    }
    
    @Override
    public void deleteDocument(Long id) {
        VendorDocument document = getDocument(id);
        vendorDocumentRepository.delete(document);
    }
    
    @Override
    public VendorDocument verifyDocument(Long id, String verifiedBy) {
        VendorDocument document = getDocument(id);
        document.setIsValid(true);
        document.setVerifiedAt(java.time.LocalDateTime.now());
        document.setVerifiedBy(verifiedBy);
        return vendorDocumentRepository.save(document);
    }
}