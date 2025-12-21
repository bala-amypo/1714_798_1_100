// com.example.demo.dto.VendorRequest.java
package com.example.demo.dto;

public class VendorRequest {
    private String vendorName;
    private String email;
    private String phone;
    private String industry;
    
    // Getters and setters
    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
}