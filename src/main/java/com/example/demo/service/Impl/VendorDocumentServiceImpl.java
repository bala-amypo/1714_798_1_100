package com.example.demo.service.impl;

import com.example.demo.model.DocumentType;
import com.example.demo.model.Vendor;
import com.example.demo.model.VendorDocument;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.repository.VendorDocumentRepository;
import com.example.demo.repository.VendorRepository;
import com.example.demo.service.VendorDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class VendorDocumentServiceImpl implements VendorDocumentService {
    
    @Autowired
    private VendorDocumentRepository vendorDocumentRepository;
    
    @Autowired
    private VendorRepository vendorRepository;
    
    @Autowired
    private DocumentTypeRepository documentTypeRepository;
    
    @Override
    public VendorDocument uploadDocument(Long vendorId, Long typeId, VendorDocument document) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new NoSuchElementException("Vendor not found with id: " + vendorId));
        
        DocumentType documentType = documentTypeRepository.findById(typeId)
                .orElseThrow(() -> new NoSuchElementException("Document type not found with id: " + typeId));
        
        if (document.getFileUrl() == null || document.getFileUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("File URL is required");
        }
        
        if (document.getExpiryDate() != null && document.getExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expiry date cannot be in the past");
        }
        
        document.setVendor(vendor);
        document.setDocumentType(documentType);
        
        return vendorDocumentRepository.save(document);
    }
    
    @Override
    public List<VendorDocument> getDocumentsForVendor(Long vendorId) {
        if (!vendorRepository.existsById(vendorId)) {
            throw new NoSuchElementException("Vendor not found with id: " + vendorId);
        }
        return vendorDocumentRepository.findByVendorId(vendorId);
    }
    
    @Override
    public VendorDocument getDocument(Long id) {
        return vendorDocumentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Vendor document not found with id: " + id));
    }
    
    @Override
    public List<VendorDocument> getAllDocuments() {
        return vendorDocumentRepository.findAll();
    }
    
    @Override
    public VendorDocument getDocumentById(Long id) {
        return getDocument(id);
    }
    
    @Override
    public VendorDocument updateDocument(Long id, Long vendorId, Long typeId, VendorDocument document) {
        VendorDocument existingDocument = getDocument(id);
        
        // Update vendor if provided and different
        if (vendorId != null && !vendorId.equals(existingDocument.getVendor().getId())) {
            Vendor vendor = vendorRepository.findById(vendorId)
                    .orElseThrow(() -> new NoSuchElementException("Vendor not found with id: " + vendorId));
            existingDocument.setVendor(vendor);
        }
        
        // Update document type if provided and different
        if (typeId != null && !typeId.equals(existingDocument.getDocumentType().getId())) {
            DocumentType documentType = documentTypeRepository.findById(typeId)
                    .orElseThrow(() -> new NoSuchElementException("Document type not found with id: " + typeId));
            existingDocument.setDocumentType(documentType);
        }
        
        // Update other fields if provided
        if (document.getFileUrl() != null && !document.getFileUrl().trim().isEmpty()) {
            existingDocument.setFileUrl(document.getFileUrl());
        }
        
        if (document.getFileName() != null) {
            existingDocument.setFileName(document.getFileName());
        }
        
        if (document.getUploadDate() != null) {
            existingDocument.setUploadDate(document.getUploadDate());
        }
        
        if (document.getExpiryDate() != null) {
            if (document.getExpiryDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Expiry date cannot be in the past");
            }
            existingDocument.setExpiryDate(document.getExpiryDate());
        }
        
        if (document.getStatus() != null) {
            existingDocument.setStatus(document.getStatus());
        }
        
        if (document.getVerifiedBy() != null) {
            existingDocument.setVerifiedBy(document.getVerifiedBy());
        }
        
        if (document.getVerificationDate() != null) {
            existingDocument.setVerificationDate(document.getVerificationDate());
        }
        
        if (document.getNotes() != null) {
            existingDocument.setNotes(document.getNotes());
        }
        
        return vendorDocumentRepository.save(existingDocument);
    }
    
    @Override
    public void deleteDocument(Long id) {
        if (!vendorDocumentRepository.existsById(id)) {
            throw new NoSuchElementException("Vendor document not found with id: " + id);
        }
        vendorDocumentRepository.deleteById(id);
    }
}