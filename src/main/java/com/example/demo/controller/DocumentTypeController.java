package com.example.demo.controller;

import com.example.demo.model.DocumentType;
import com.example.demo.service.DocumentTypeService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/document-types")
@Tag(name = "Document Types")
public class DocumentTypeController {

    private final DocumentTypeService service;

    public DocumentTypeController(DocumentTypeService service) {
        this.service = service;
    }

    @PostMapping
    public DocumentType create(@RequestBody DocumentType d) {
        return service.createDocumentType(d);
    }

    @GetMapping
    public List<DocumentType> getAll() {
        return service.getAllDocumentTypes();
    }

    @GetMapping("/{id}")
    public DocumentType get(@PathVariable Long id) {
        return service.getDocumentType(id);
    }
}
