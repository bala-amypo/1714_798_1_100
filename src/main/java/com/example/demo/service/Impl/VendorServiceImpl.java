package com.example.demo.service.impl;

import com.example.demo.model.Vendor;
import com.example.demo.repository.VendorRepository;
import com.example.demo.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class VendorServiceImpl implements VendorService {
    
    @Autowired
    private VendorRepository vendorRepository;
    
    @Override
    public Vendor createVendor(Vendor vendor) {
        if (vendorRepository.existsByVendorName(vendor.getVendorName())) {
            throw new IllegalArgumentException("Vendor name already exists: " + vendor.getVendorName());
        }
        return vendorRepository.save(vendor);
    }
    
    @Override
    public Vendor getVendor(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Vendor not found with id: " + id));
    }
    
    @Override
    public Vendor getVendorById(Long id) {
        return getVendor(id);
    }
    
    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }
    
    @Override
    public Vendor updateVendor(Vendor vendor) {
        Vendor existingVendor = getVendor(vendor.getId());
        
        // Update fields if provided
        if (vendor.getVendorName() != null && !vendor.getVendorName().equals(existingVendor.getVendorName())) {
            // Check if new vendor name already exists (excluding current vendor)
            if (vendorRepository.existsByVendorName(vendor.getVendorName())) {
                throw new IllegalArgumentException("Vendor name already exists: " + vendor.getVendorName());
            }
            existingVendor.setVendorName(vendor.getVendorName());
        }
        
        if (vendor.getContactEmail() != null) {
            existingVendor.setContactEmail(vendor.getContactEmail());
        }
        
        if (vendor.getContactPhone() != null) {
            existingVendor.setContactPhone(vendor.getContactPhone());
        }
        
        if (vendor.getAddress() != null) {
            existingVendor.setAddress(vendor.getAddress());
        }
        
        if (vendor.getIndustry() != null) {
            existingVendor.setIndustry(vendor.getIndustry());
        }
        
        if (vendor.getStatus() != null) {
            existingVendor.setStatus(vendor.getStatus());
        }
        
        return vendorRepository.save(existingVendor);
    }
    
    @Override
    public void deleteVendor(Long id) {
        if (!vendorRepository.existsById(id)) {
            throw new NoSuchElementException("Vendor not found with id: " + id);
        }
        vendorRepository.deleteById(id);
    }
}