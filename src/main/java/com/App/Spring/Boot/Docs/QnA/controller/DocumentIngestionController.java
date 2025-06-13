package com.App.Spring.Boot.Docs.QnA.controller;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.service.DocumentIngestionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documents/ingest")
public class DocumentIngestionController {
    private final DocumentIngestionService ingestionService;

    public DocumentIngestionController(DocumentIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping
    public ResponseEntity<String> ingestSingle(@Valid @RequestBody DocumentDTO documentDTO) {
        ingestionService.ingestDocument(documentDTO);
        return ResponseEntity.ok("Document ingestion queued");
    }

    @PostMapping("/batch")
    public ResponseEntity<String> ingestBatch(@RequestBody List<@Valid DocumentDTO> documents) {
        ingestionService.ingestBatchDocuments(documents);
        return ResponseEntity.ok("Batch ingestion job started");
    }

    @PostMapping("/file")
    public ResponseEntity<String> ingestFile(@RequestParam("file") MultipartFile file) {
        ingestionService.ingestFile(file);
        return ResponseEntity.ok("File ingestion queued");
    }
}
