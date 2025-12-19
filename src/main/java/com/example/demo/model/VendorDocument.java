package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class VendorDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vendor vendor;

    @ManyToOne
    private DocumentType documentType;

    private String fileUrl;

    private LocalDateTime uploadedAt;

    private LocalDate expiryDate;

    private Boolean isValid;

    @PrePersist
    public void prePersist() {
        uploadedAt = LocalDateTime.now();
    }

    // getters and setters
}
