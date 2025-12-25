package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "document_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String typeName;
    
    private String description;
    private Boolean required = false;
    
    @Column(nullable = false)
    private Integer weight = 0;
    
    private LocalDateTime createdAt;
    
    @ManyToMany(mappedBy = "supportedDocumentTypes")
    private Set<Vendor> vendors = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (weight == null) {
            weight = 0;
        }
        if (required == null) {
            required = false;
        }
    }
}