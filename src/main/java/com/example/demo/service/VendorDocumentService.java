package com.example.demo.service;

import com.example.demo.model.VendorDocument;
import java.util.List;
import java.util.Optional;

public interface VendorDocumentService {
    VendorDocument uploadDocument(Long vendorId, Long typeId, VendorDocument document);
    List<VendorDocument> getDocumentsForVendor(Long vendorId);
    VendorDocument getDocument(Long id);
    void deleteDocument(Long id);
}