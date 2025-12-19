// src/main/java/com/example/demo/repository/VendorRepository.java
package com.example.demo.repository;

import com.example.demo.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    boolean existsByVendorName(String vendorName);
}