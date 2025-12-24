package com.example.demo.service.impl;

import com.example.demo.model.Vendor;
import com.example.demo.repository.VendorRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.VendorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VendorServiceImpl implements VendorService {
    
    private final VendorRepository vendorRepository;
    
    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }
    
    @Override
    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }
    
    @Override
    public Vendor getVendor(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + id));
    }
    
    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }
    
    @Override
    public Vendor updateVendor(Long id, Vendor vendorDetails) {
        Vendor vendor = getVendor(id);
        vendor.setVendorName(vendorDetails.getVendorName());
        vendor.setIndustry(vendorDetails.getIndustry());
        vendor.setSupportedDocumentTypes(vendorDetails.getSupportedDocumentTypes());
        return vendorRepository.save(vendor);
    }
    
    @Override
    public void deleteVendor(Long id) {
        Vendor vendor = getVendor(id);
        vendorRepository.delete(vendor);
    }
}