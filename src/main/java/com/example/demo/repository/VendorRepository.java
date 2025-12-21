// com.example.demo.repository.VendorRepository.java
package com.example.demo.repository;

import com.example.demo.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    boolean existsByVendorName(String vendorName);
    Optional<Vendor> findByVendorName(String vendorName);
}