package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class VendorDTO {
    private Long id;
    private String vendorName;
    private String email;
    private String phone;
    private String industry;
    private LocalDateTime createdAt;
    private Set<Long> documentTypeIds;
    
    public VendorDTO() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Set<Long> getDocumentTypeIds() { return documentTypeIds; }
    public void setDocumentTypeIds(Set<Long> documentTypeIds) { this.documentTypeIds = documentTypeIds; }
}