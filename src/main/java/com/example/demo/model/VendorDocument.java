package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_documents")
public class VendorDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;
    
    @ManyToOne
    @JoinColumn(name = "document_type_id", nullable = false)
    private DocumentType documentType;
    
    @Column(nullable = false)
    private String fileUrl;
    
    @Column(nullable = false)
    private LocalDateTime uploadedAt;
    
    private LocalDate expiryDate;
    
    private Boolean isValid = true;
    
    public VendorDocument() {
    }
    
    public VendorDocument(Vendor vendor, DocumentType documentType, String fileUrl) {
        this.vendor = vendor;
        this.documentType = documentType;
        this.fileUrl = fileUrl;
    }
    
    public VendorDocument(Vendor vendor, DocumentType documentType, String fileUrl, LocalDate expiryDate) {
        this.vendor = vendor;
        this.documentType = documentType;
        this.fileUrl = fileUrl;
        this.expiryDate = expiryDate;
    }
    
    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDateTime.now();
        if (expiryDate != null && expiryDate.isBefore(LocalDate.now())) {
            isValid = false;
        }
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Vendor getVendor() { return vendor; }
    public void setVendor(Vendor vendor) { this.vendor = vendor; }
    public DocumentType getDocumentType() { return documentType; }
    public void setDocumentType(DocumentType documentType) { this.documentType = documentType; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { 
        this.expiryDate = expiryDate; 
        if (expiryDate != null && expiryDate.isBefore(LocalDate.now())) {
            isValid = false;
        }
    }
    public Boolean getIsValid() { return isValid; }
    public void setIsValid(Boolean isValid) { this.isValid = isValid; }
}