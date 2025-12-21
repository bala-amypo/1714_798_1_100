package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Vendor;
import com.example.demo.service.VendorService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    VendorService vendorService;

    @PostMapping
    public Vendor createVendor(@RequestBody Vendor vendor) {
        return vendorService.createVendor(vendor);
    }

    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }

    @GetMapping("/{id}")
    public Optional<Vendor> getVendor(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @PutMapping("/{id}")
    public String updateVendor(@PathVariable Long id, @RequestBody Vendor newVendor) {
        Optional<Vendor> vendor = vendorService.getVendorById(id);
        if (vendor.isPresent()) {
            newVendor.setId(id);
            vendorService.updateVendor(newVendor);
            return "Vendor Updated Successfully";
        }
        return "Vendor not found";
    }

    @DeleteMapping("/{id}")
    public String deleteVendor(@PathVariable Long id) {
        Optional<Vendor> vendor = vendorService.getVendorById(id);
        if (vendor.isPresent()) {
            vendorService.deleteVendor(id);
            return "Vendor Deleted Successfully";
        }
        return "Vendor not found";
    }
}