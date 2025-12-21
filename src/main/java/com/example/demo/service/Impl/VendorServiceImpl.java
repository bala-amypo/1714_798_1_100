package com.example.demo.service;

import com.example.demo.model.Vendor;
import com.example.demo.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class VendorServiceImpl implements VendorService {
    
    private final VendorRepository vendorRepository;
    
    @Autowired
    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }
    
    @Override
    public Vendor createVendor(Vendor vendor) {
        // Enforce unique vendorName
        if (vendorRepository.existsByVendorName(vendor.getVendorName())) {
            throw new RuntimeException("Duplicate vendor name");
        }
        
        return vendorRepository.save(vendor);
    }
    
    @Override
    public Vendor getVendor(Long id) {
        return vendorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vendor not found"));
    }
    
    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }
}