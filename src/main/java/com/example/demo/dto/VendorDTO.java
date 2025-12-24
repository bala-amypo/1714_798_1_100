package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public class VendorDTO {
    
    private Long id;
    
    @NotBlank(message = "Vendor name is required")
    private String vendorName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    private String phone;
    private String industry;
    private Set<Long> supportedDocumentTypeIds;
    
    public VendorDTO() {}
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getVendorName() {
        return vendorName;
    }
    
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getIndustry() {
        return industry;
    }
    
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    
    public Set<Long> getSupportedDocumentTypeIds() {
        return supportedDocumentTypeIds;
    }
    
    public void setSupportedDocumentTypeIds(Set<Long> supportedDocumentTypeIds) {
        this.supportedDocumentTypeIds = supportedDocumentTypeIds;
    }
}