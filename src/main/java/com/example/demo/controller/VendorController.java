package com.example.demo.controller;

import com.example.demo.dto.VendorDTO;
import com.example.demo.model.Vendor;
import com.example.demo.service.VendorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
    
    private final VendorService vendorService;
    
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }
    
    @PostMapping
    public ResponseEntity<VendorDTO> createVendor(@Valid @RequestBody VendorDTO vendorDTO) {
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorDTO.getVendorName());
        vendor.setEmail(vendorDTO.getEmail());
        vendor.setPhone(vendorDTO.getPhone());
        vendor.setIndustry(vendorDTO.getIndustry());
        
        Vendor savedVendor = vendorService.createVendor(vendor);
        return new ResponseEntity<>(convertToDTO(savedVendor), HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<VendorDTO>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        List<VendorDTO> vendorDTOs = vendors.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vendorDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VendorDTO> getVendor(@PathVariable Long id) {
        Vendor vendor = vendorService.getVendor(id);
        return ResponseEntity.ok(convertToDTO(vendor));
    }
    
    private VendorDTO convertToDTO(Vendor vendor) {
        VendorDTO dto = new VendorDTO();
        dto.setId(vendor.getId());
        dto.setVendorName(vendor.getVendorName());
        dto.setEmail(vendor.getEmail());
        dto.setPhone(vendor.getPhone());
        dto.setIndustry(vendor.getIndustry());
        return dto;
    }
}