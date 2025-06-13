package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import com.App.Spring.Boot.Docs.QnA.exception.ApiException;
import com.App.Spring.Boot.Docs.QnA.repository.DocumentRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QnaService {
    private final DocumentRepository documentRepository;

    public QnaService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Cacheable(value = "qnaCache", key = "#question")
    public Page<DocumentDTO> answerQuestion(String question, Pageable pageable) {
        if (question == null || question.trim().isEmpty()) {
            throw new ApiException("Question cannot be empty", 400);
        }
        Page<Document> documents = documentRepository.searchDocuments(question, pageable);
        return documents.map(doc -> {
            DocumentDTO dto = new DocumentDTO();
            dto.setTitle(doc.getTitle());
            dto.setContent(doc.getContent());
            dto.setAuthor(doc.getAuthor());
            dto.setType(doc.getType());
            dto.setKeywords(doc.getKeywords());
            return dto;
        });
    }
}
