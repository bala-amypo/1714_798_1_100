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
    public void deleteDocument(Long id) {
        if (!vendorDocumentRepository.existsById(id)) {
            throw new NoSuchElementException("Vendor document not found with id: " + id);
        }
        vendorDocumentRepository.deleteById(id);
    }
}