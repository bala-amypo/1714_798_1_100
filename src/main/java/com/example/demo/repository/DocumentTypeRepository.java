// src/main/java/com/example/demo/repository/DocumentTypeRepository.java
package com.example.demo.repository;

import com.example.demo.model.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
    boolean existsByTypeName(String typeName);
    List<DocumentType> findByRequiredTrue();
}