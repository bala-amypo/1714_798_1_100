// src/main/java/com/example/demo/dto/DocumentTypeRequest.java
package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class DocumentTypeRequest {
    
    @NotBlank
    private String typeName;
    
    private String description;
    private Boolean required = false;
    
    @Min(0)
    private Integer weight = 0;
    
    // Getters and Setters
    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Boolean getRequired() { return required; }
    public void setRequired(Boolean required) { this.required = required; }
    
    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }
}