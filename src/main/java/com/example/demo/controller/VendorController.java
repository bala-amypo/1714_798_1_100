package com.example.demo.controller;

import com.example.demo.dto.VendorDTO;
import com.example.demo.model.Vendor;
import com.example.demo.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
    
    @Autowired
    private VendorService vendorService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping
    public ResponseEntity<VendorDTO> createVendor(@RequestBody Vendor vendor) {
        Vendor createdVendor = vendorService.createVendor(vendor);
        return ResponseEntity.ok(convertToDto(createdVendor));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VendorDTO> getVendor(@PathVariable Long id) {
        Vendor vendor = vendorService.getVendor(id);
        return ResponseEntity.ok(convertToDto(vendor));
    }
    
    @GetMapping
    public ResponseEntity<List<VendorDTO>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        List<VendorDTO> vendorDTOs = vendors.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vendorDTOs);
    }
    
    private VendorDTO convertToDto(Vendor vendor) {
        VendorDTO dto = modelMapper.map(vendor, VendorDTO.class);
        if (vendor.getSupportedDocumentTypes() != null) {
            dto.setDocumentTypeIds(vendor.getSupportedDocumentTypes().stream()
                    .map(dt -> dt.getId())
                    .collect(Collectors.toSet()));
        }
        return dto;
    }
}