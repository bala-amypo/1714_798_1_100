package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class VendorDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Vendor vendor;

    @ManyToOne(optional = false)
    private DocumentType documentType;

    @Column(nullable = false)
    private String fileUrl;

    private LocalDateTime uploadedAt;

    private LocalDate expiryDate;

    @Column(nullable = false)
    private Boolean isValid;

    @PrePersist
    public void prePersist() {
        this.uploadedAt = LocalDateTime.now();
    }

    // -------- Getters --------

    public Long getId() {
        return id;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    // -------- Setters --------

    public void setId(Long id) {
        this.id = id;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}
