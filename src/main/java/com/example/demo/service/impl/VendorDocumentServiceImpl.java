package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.VendorDocumentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VendorDocumentServiceImpl implements VendorDocumentService {

    private final VendorDocumentRepository docRepo;
    private final VendorRepository vendorRepo;
    private final DocumentTypeRepository typeRepo;

    public VendorDocumentServiceImpl(VendorDocumentRepository docRepo,
                                     VendorRepository vendorRepo,
                                     DocumentTypeRepository typeRepo) {
        this.docRepo = docRepo;
        this.vendorRepo = vendorRepo;
        this.typeRepo = typeRepo;
    }

    @Override
    public VendorDocument uploadDocument(Long vendorId, Long typeId, VendorDocument document) {

        Vendor vendor = vendorRepo.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        DocumentType type = typeRepo.findById(typeId)
                .orElseThrow(() -> new ResourceNotFoundException("Document type not found"));

        if (document.getExpiryDate() != null &&
            document.getExpiryDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Document has expired");
        }

        document.setVendor(vendor);
        document.setDocumentType(type);
        document.setIsValid(document.getExpiryDate() == null ||
                document.getExpiryDate().isAfter(LocalDate.now()));

        return docRepo.save(document);
    }

    @Override
    public List<VendorDocument> getDocumentsForVendor(Long vendorId) {
        return docRepo.findByVendor_Id(vendorId);
    }

    @Override
    public VendorDocument getDocument(Long id) {
        return docRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));
    }
}
