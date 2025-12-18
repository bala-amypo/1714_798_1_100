package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "typeName"))
public class DocumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeName;
    private String description;
    private Boolean required;
    private Integer weight;

    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getTypeName() { return typeName; }
    public String getDescription() { return description; }
    public Boolean getRequired() { return required; }
    public Integer getWeight() { return weight; }

    public void setId(Long id) { this.id = id; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public void setDescription(String description) { this.description = description; }
    public void setRequired(Boolean required) { this.required = required; }
    public void setWeight(Integer weight) { this.weight = weight; }
}