// src/main/java/com/example/demo/controller/VendorController.java
package com.example.demo.controller;

import com.example.demo.dto.VendorRequest;
import com.example.demo.model.Vendor;
import com.example.demo.service.VendorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
    
    private final VendorService vendorService;
    
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }
    
    @PostMapping
    public Vendor createVendor(@Valid @RequestBody VendorRequest vendorRequest) {
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorRequest.getVendorName());
        vendor.setEmail(vendorRequest.getEmail());
        vendor.setPhone(vendorRequest.getPhone());
        vendor.setIndustry(vendorRequest.getIndustry());
        
        return vendorService.createVendor(vendor);
    }
    
    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }
    
    @GetMapping("/{id}")
    public Vendor getVendor(@PathVariable Long id) {
        return vendorService.getVendor(id);
    }
}