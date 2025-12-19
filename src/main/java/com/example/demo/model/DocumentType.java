package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DocumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String typeName;

    private String description;

    @Column(nullable = false)
    private Boolean required;

    private Integer weight;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // -------- Getters --------

    public Long getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getRequired() {
        return required;
    }

    public Integer getWeight() {
        return weight;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // -------- Setters --------

    public void setId(Long id) {
        this.id = id;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
