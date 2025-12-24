package com.example.demo.controller;
import com.example.demo.model.VendorDocument;
import com.example.demo.service.VendorDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vendor-documents")
public class VendorDocumentController {
    
        private final VendorDocumentService vendorDocumentService;
            
                @Autowired
                    public VendorDocumentController(VendorDocumentService vendorDocumentService) {
                            this.vendorDocumentService = vendorDocumentService;
                                }
                                    
                                        @PostMapping
                                            public ResponseEntity<VendorDocument> uploadDocument(
                                                        @RequestParam Long vendorId,
                                                                    @RequestParam Long typeId,
                                                                                @RequestBody VendorDocument document) {
                                                                                        VendorDocument uploadedDocument = vendorDocumentService.uploadDocument(vendorId, typeId, document);
                                                                                                return new ResponseEntity<>(uploadedDocument, HttpStatus.CREATED);
                                                                                                    }
                                                                                                        
                                                                                                            @GetMapping("/vendor/{vendorId}")
                                                                                                                public ResponseEntity<List<VendorDocument>> getDocumentsForVendor(@PathVariable Long vendorId) {
                                                                                                                        List<VendorDocument> documents = vendorDocumentService.getDocumentsForVendor(vendorId);
                                                                                                                                return ResponseEntity.ok(documents);
                                                                                                                                    }
                                                                                                                                        
                                                                                                                                            @GetMapping("/{id}")
                                                                                                                                                public ResponseEntity<VendorDocument> getDocumentById(@PathVariable Long id) {
                                                                                                                                                        VendorDocument document = vendorDocumentService.getDocument(id);
                                                                                                                                                                return ResponseEntity.ok(document);
                                                                                                                                                                    }
                                                                                                                                                                        
                                                                                                                                                                            @GetMapping("/expired")
                                                                                                                                                                                public ResponseEntity<List<VendorDocument>> getExpiredDocuments() {
                                                                                                                                                                                        List<VendorDocument> documents = vendorDocumentService.getExpiredDocuments();
                                                                                                                                                                                                return ResponseEntity.ok(documents);
                                                                                                                                                                                                    }
                                                                                                                                                                                                        
                                                                                                                                                                                                            @PutMapping("/{id}")
                                                                                                                                                                                                                public ResponseEntity<VendorDocument> updateDocument(@PathVariable Long id, @RequestBody VendorDocument document) {
                                                                                                                                                                                                                        VendorDocument updatedDocument = vendorDocumentService.updateDocument(id, document);
                                                                                                                                                                                                                                return ResponseEntity.ok(updatedDocument);
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                        
                                                                                                                                                                                                                                            @DeleteMapping("/{id}")
                                                                                                                                                                                                                                                public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
                                                                                                                                                                                                                                                        vendorDocumentService.deleteDocument(id);
                                                                                                                                                                                                                                                                return ResponseEntity.noContent().build();
                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                    }