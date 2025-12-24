package com.example.demo.repository;

import com.example.demo.model.VendorDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface VendorDocumentRepository extends JpaRepository<VendorDocument, Long> {
    
    List<VendorDocument> findByVendorId(Long vendorId);
    
    List<VendorDocument> findByVendorIdAndDocumentTypeId(Long vendorId, Long documentTypeId);
    
    @Query("SELECT vd FROM VendorDocument vd WHERE vd.expiryDate < :cutoffDate")
    List<VendorDocument> findExpiredDocuments(LocalDate cutoffDate);
}