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
    
    @Column(nullable = false)
    private Boolean isValid;
    
    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDateTime.now();
        // Auto-set isValid based on expiry date
        if (expiryDate != null) {
            isValid = !expiryDate.isBefore(LocalDate.now());
        } else {
            isValid = true;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        // Re-validate on update
        if (expiryDate != null) {
            isValid = !expiryDate.isBefore(LocalDate.now());
        } else {
            isValid = true;
        }
    }
    
    public VendorDocument() {}
    
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
        // Update isValid when expiryDate is set
        if (expiryDate != null) {
            this.isValid = !expiryDate.isBefore(LocalDate.now());
        } else {
            this.isValid = true;
        }
    }
    
    public Boolean getIsValid() { return isValid; }
    public void setIsValid(Boolean isValid) { this.isValid = isValid; }
}