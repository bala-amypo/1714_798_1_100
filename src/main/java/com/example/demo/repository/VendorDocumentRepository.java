package com.example.demo.repository;

import com.example.demo.model.Vendor;
import com.example.demo.model.VendorDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VendorDocumentRepository extends JpaRepository<VendorDocument, Long> {
    List<VendorDocument> findByVendor(Vendor vendor);
    List<VendorDocument> findByVendorId(Long vendorId);
    List<VendorDocument> findByDocumentTypeId(Long documentTypeId);
    Optional<VendorDocument> findByVendorIdAndDocumentTypeId(Long vendorId, Long documentTypeId);
    
    @Query("SELECT vd FROM VendorDocument vd WHERE vd.expiryDate < :currentDate AND vd.isValid = true")
    List<VendorDocument> findExpiredDocuments(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT vd FROM VendorDocument vd WHERE vd.vendor.id = :vendorId AND vd.documentType.required = true")
    List<VendorDocument> findRequiredDocumentsByVendor(@Param("vendorId") Long vendorId);
}