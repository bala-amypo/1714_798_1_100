package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class VendorDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vendor vendor;

    @ManyToOne
    private DocumentType documentType;

    private String fileUrl;
    private LocalDateTime uploadedAt;
    private LocalDate expiryDate;
    private Boolean isValid;

    @PrePersist
    void prePersist() {
        uploadedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Vendor getVendor() { return vendor; }
    public DocumentType getDocumentType() { return documentType; }
    public String getFileUrl() { return fileUrl; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public Boolean getIsValid() { return isValid; }

    public void setVendor(Vendor vendor) { this.vendor = vendor; }
    public void setDocumentType(DocumentType documentType) { this.documentType = documentType; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public void setIsValid(Boolean isValid) { this.isValid = isValid; }
}