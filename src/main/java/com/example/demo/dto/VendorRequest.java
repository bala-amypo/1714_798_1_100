// src/main/java/com/example/demo/dto/VendorRequest.java
package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class VendorRequest {
    
    @NotBlank
    private String vendorName;
    
    private String email;
    private String phone;
    private String industry;
    
    // Getters and Setters
    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
}