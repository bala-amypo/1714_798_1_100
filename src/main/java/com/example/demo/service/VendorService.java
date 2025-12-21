package com.example.demo.service;

import com.example.demo.model.Vendor;
import java.util.List;

public interface VendorService {
    Vendor createVendor(Vendor vendor);
    Vendor getVendor(Long id);
    Vendor getVendorById(Long id); // Add this
    List<Vendor> getAllVendors();
    Vendor updateVendor(Vendor vendor); // Add this
    void deleteVendor(Long id);
}