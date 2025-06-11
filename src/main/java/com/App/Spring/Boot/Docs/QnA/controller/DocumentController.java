package com.App.Spring.Boot.Docs.QnA.controller;
import com.App.Spring.Boot.Docs.QnA.batch.DocumentItemReader;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import com.App.Spring.Boot.Docs.QnA.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller for document ingestion and retrieval endpoints.
 */
@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job documentIngestionJob;

    @Autowired
    private DocumentItemReader documentItemReader;

    @PostMapping("/ingest")
    public ResponseEntity<String> ingest(@RequestBody DocumentDTO dto) {
        try {
            documentService.ingestDocument(dto);
            return ResponseEntity.ok("Document ingestion queued");
        } catch (Exception e) {
            logger.error("Failed to queue document ingestion: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/ingest/batch")
    public ResponseEntity<String> ingestBatch(@RequestBody List<DocumentDTO> dtos) {
        try {
            if (dtos == null || dtos.isEmpty()) {
                return ResponseEntity.badRequest().body("No documents provided for batch ingestion");
            }
            logger.info("Starting batch ingestion for {} documents", dtos.size());
            documentItemReader.setDocuments(dtos); // Set documents for the reader
            JobParameters params = new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(documentIngestionJob, params);
            return ResponseEntity.ok("Batch ingestion job started");
        } catch (Exception e) {
            logger.error("Failed to start batch ingestion: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Document>> getDocuments(
            @RequestParam String author,
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok(documentService.getDocuments(author, type, page, size));
        } catch (Exception e) {
            logger.error("Failed to retrieve documents: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
}