package com.App.Spring.Boot.Docs.QnA.controller;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.service.DocumentRetrievalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentRetrievalController {
    private final DocumentRetrievalService retrievalService;

    public DocumentRetrievalController(DocumentRetrievalService retrievalService) {
        this.retrievalService = retrievalService;
    }

    @GetMapping
    public ResponseEntity<Page<DocumentDTO>> getDocuments(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String type,
            Pageable pageable) {
        return ResponseEntity.ok(retrievalService.getDocuments(author, type, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DocumentDTO>> searchDocuments(
            @RequestParam String query,
            Pageable pageable) {
        return ResponseEntity.ok(retrievalService.searchDocuments(query, pageable));
    }
}
