package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VendorDocumentDTO {
    private Long id;
    private Long vendorId;
    private Long documentTypeId;
    private String fileUrl;
    private LocalDateTime uploadedAt;
    private LocalDate expiryDate;
    private Boolean isValid;
    private String vendorName;
    private String documentTypeName;
    
    public VendorDocumentDTO() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getVendorId() {
        return vendorId;
    }
    
    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }
    
    public Long getDocumentTypeId() {
        return documentTypeId;
    }
    
    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }
    
    public String getFileUrl() {
        return fileUrl;
    }
    
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    
    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
    
    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public Boolean getIsValid() {
        return isValid;
    }
    
    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
    
    public String getVendorName() {
        return vendorName;
    }
    
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    
    public String getDocumentTypeName() {
        return documentTypeName;
    }
    
    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }
}