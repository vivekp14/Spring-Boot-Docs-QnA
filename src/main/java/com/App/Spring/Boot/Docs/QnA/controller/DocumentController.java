package com.App.Spring.Boot.Docs.QnA.controller;

import com.App.Spring.Boot.Docs.QnA.dto.DocumentResponse;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentUploadRequest;
import com.App.Spring.Boot.Docs.QnA.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @PostMapping("/upload")
    public ResponseEntity<DocumentResponse> uploadDocument(@RequestBody DocumentUploadRequest request) {
        logger.info("Uploading document: {}", request.getFilename());
        DocumentResponse response = documentService.uploadDocument(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<DocumentResponse>> batchUpload(@RequestBody List<DocumentUploadRequest> requests) {
        logger.info("Batch uploading {} documents", requests.size());
        List<DocumentResponse> responses = documentService.batchUpload(requests);
        return ResponseEntity.ok(responses);
    }

    @GetMapping
    public ResponseEntity<Page<DocumentResponse>> filterDocuments(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String type,
            Pageable pageable
    ) {
        logger.info("Filtering documents by author: {}, type: {}", author, type);
        Page<DocumentResponse> page = documentService.filterDocuments(author, type, pageable);
        return ResponseEntity.ok(page);
    }
}
