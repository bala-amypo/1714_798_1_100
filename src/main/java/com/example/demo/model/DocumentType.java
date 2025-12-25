package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "document_types")
@Getter
@Setter
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
    
    @ManyToMany(mappedBy = "supportedDocumentTypes", fetch = FetchType.LAZY)
    private Set<Vendor> vendors = new HashSet<>();
    
    @PrePersist
    public void prePersist() {
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
    
    // Custom equals and hashCode to avoid recursion
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentType)) return false;
        return id != null && id.equals(((DocumentType) o).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return "DocumentType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", weight=" + weight +
                '}';
    }
}