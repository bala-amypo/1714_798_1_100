package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;
    
    @ManyToOne
    @JoinColumn(name = "document_type_id", nullable = false)
    private DocumentType documentType;
    
    @Column(nullable = false)
    private String fileUrl;
    
    private LocalDateTime uploadedAt;
    
    private LocalDate expiryDate;
    
    private Boolean isValid;
    
   @PrePersist
public void prePersist() {
    if (uploadedAt == null) {
        uploadedAt = LocalDateTime.now();
    }
    if (expiryDate != null) {
        isValid = !expiryDate.isBefore(LocalDate.now());
    } else if (isValid == null) {
        isValid = true;
    }
}
}