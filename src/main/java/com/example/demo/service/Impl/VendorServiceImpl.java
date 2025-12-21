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
        if (vendorRepository.existsByVendorName(vendor.getVendorName())) {
            throw new RuntimeException("Duplicate vendor name: " + vendor.getVendorName());
        }
        
        return vendorRepository.save(vendor);
    }
    
    @Override
    public Vendor getVendor(Long id) {
        return vendorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vendor not found with ID: " + id));
    }
    
    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }
    
    @Override
    public Vendor updateVendor(Long id, Vendor vendor) {
        Vendor existingVendor = getVendor(id);
        
        if (!existingVendor.getVendorName().equals(vendor.getVendorName()) && 
            vendorRepository.existsByVendorName(vendor.getVendorName())) {
            throw new RuntimeException("Duplicate vendor name: " + vendor.getVendorName());
        }
        
        existingVendor.setVendorName(vendor.getVendorName());
        existingVendor.setEmail(vendor.getEmail());
        existingVendor.setPhone(vendor.getPhone());
        existingVendor.setIndustry(vendor.getIndustry());
        
        return vendorRepository.save(existingVendor);
    }
    
    @Override
    public void deleteVendor(Long id) {
        Vendor vendor = getVendor(id);
        vendorRepository.delete(vendor);
    }
}