package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import com.App.Spring.Boot.Docs.QnA.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @Async("taskExecutor")
    public void ingestDocument(DocumentDTO dto) {
        Document document = new Document();
        document.setTitle(dto.getTitle());
        document.setContent(dto.getContent());
        document.setAuthor(dto.getAuthor());
        document.setType(dto.getType());
        document.setCreatedAt(LocalDateTime.now());
        documentRepository.save(document);
    }

    public List<Document> getDocuments(String author, String type, int page, int size) {
        return documentRepository.findByMetadata(author, type, Pageable.ofSize(size).withPage(page)).getContent();
    }
}