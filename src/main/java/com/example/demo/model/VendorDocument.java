package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;
    
    @ManyToOne(fetch = FetchType.LAZY)
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VendorDocument)) return false;
        return id != null && id.equals(((VendorDocument) o).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return "VendorDocument{" +
                "id=" + id +
                ", fileUrl='" + fileUrl + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}