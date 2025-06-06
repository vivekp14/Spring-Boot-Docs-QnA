package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentResponse;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentUploadRequest;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import com.App.Spring.Boot.Docs.QnA.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);

    public DocumentResponse uploadDocument(DocumentUploadRequest request) {
        Document doc = Document.builder()
                .filename(request.getFilename())
                .type(request.getType())
                .author(request.getAuthor())
                .uploadDate(request.getUploadDate())
                .content(request.getContent())
                .keywords(request.getKeywords())
                .build();
        Document saved = documentRepository.save(doc);
        logger.info("Document {} uploaded successfully", saved.getFilename());
        return toResponse(saved);
    }

    @Async
    public List<DocumentResponse> batchUpload(List<DocumentUploadRequest> requests) {
        List<Document> docs = requests.stream().map(req -> Document.builder()
                .filename(req.getFilename())
                .type(req.getType())
                .author(req.getAuthor())
                .uploadDate(req.getUploadDate())
                .content(req.getContent())
                .keywords(req.getKeywords())
                .build()).collect(Collectors.toList());
        List<Document> saved = documentRepository.saveAll(docs);
        logger.info("Batch upload completed");
        return saved.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public Page<DocumentResponse> filterDocuments(String author, String type, Pageable pageable) {
        Page<Document> page;
        if (author != null && type != null) {
            page = documentRepository.findByAuthorAndType(author, type, pageable);
        } else if (author != null) {
            page = documentRepository.findByAuthor(author, pageable);
        } else if (type != null) {
            page = documentRepository.findByType(type, pageable);
        } else {
            page = documentRepository.findAll(pageable);
        }
        return page.map(this::toResponse);
    }

    private DocumentResponse toResponse(Document doc) {
        DocumentResponse resp = new DocumentResponse();
        resp.setId(doc.getId());
        resp.setFilename(doc.getFilename());
        resp.setType(doc.getType());
        resp.setAuthor(doc.getAuthor());
        resp.setUploadDate(doc.getUploadDate());
        resp.setSummary(doc.getSummary());
        resp.setKeywords(doc.getKeywords());
        return resp;
    }
}
