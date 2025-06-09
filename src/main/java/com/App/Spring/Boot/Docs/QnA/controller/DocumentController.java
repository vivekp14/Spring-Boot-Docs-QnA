package com.App.Spring.Boot.Docs.QnA.controller;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import com.App.Spring.Boot.Docs.QnA.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @PostMapping("/ingest")
    public ResponseEntity<String> ingest(@RequestBody DocumentDTO dto) {
        documentService.ingestDocument(dto);
        return ResponseEntity.ok("Document ingestion started");
    }

    @GetMapping
    public ResponseEntity<List<Document>> getDocuments(
            @RequestParam String author,
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(documentService.getDocuments(author, type, page, size));
    }
}