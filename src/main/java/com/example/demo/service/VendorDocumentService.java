package com.example.demo.service;

import com.example.demo.model.VendorDocument;
import java.util.List;

public interface VendorDocumentService {
    VendorDocument uploadDocument(Long vendorId, Long documentTypeId, VendorDocument document);
    VendorDocument getDocument(Long id);
    List<VendorDocument> getDocumentsByVendor(Long vendorId);
    List<VendorDocument> getExpiredDocuments();
    VendorDocument updateDocument(Long id, VendorDocument documentDetails);
    void deleteDocument(Long id);
    VendorDocument verifyDocument(Long id, String verifiedBy);
}