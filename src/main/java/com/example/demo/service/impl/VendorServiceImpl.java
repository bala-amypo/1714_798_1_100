package com.example.demo.service.impl;

import com.example.demo.exception.ValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Vendor;
import com.example.demo.repository.VendorRepository;
import com.example.demo.service.VendorService;
import java.util.List;

public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    // MUST MATCH EXACT SIGNATURE
    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public Vendor createVendor(Vendor vendor) {
        if (vendorRepository.existsByVendorName(vendor.getVendorName())) {
            throw new ValidationException("Vendor name already exists");
        }
        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor getVendor(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }
}
