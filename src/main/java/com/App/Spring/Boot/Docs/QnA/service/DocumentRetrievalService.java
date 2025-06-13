package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import com.App.Spring.Boot.Docs.QnA.exception.ApiException;
import com.App.Spring.Boot.Docs.QnA.repository.DocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DocumentRetrievalService {
    private final DocumentRepository documentRepository;

    public DocumentRetrievalService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Page<DocumentDTO> getDocuments(String author, String type, Pageable pageable) {
        Page<Document> documents = documentRepository.findByMetadata(author, type, pageable);
        return documents.map(this::toDTO);
    }

    public Page<DocumentDTO> searchDocuments(String query, Pageable pageable) {
        if (query == null || query.trim().isEmpty()) {
            throw new ApiException("Search query cannot be empty", 400);
        }
        Page<Document> documents = documentRepository.searchDocuments(query, pageable);
        return documents.map(this::toDTO);
    }

    private DocumentDTO toDTO(Document document) {
        DocumentDTO dto = new DocumentDTO();
        dto.setTitle(document.getTitle());
        dto.setContent(document.getContent());
        dto.setAuthor(document.getAuthor());
        dto.setType(document.getType());
        dto.setKeywords(document.getKeywords());
        return dto;
    }
}
