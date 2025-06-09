package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.dto.QnaRequest;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import com.App.Spring.Boot.Docs.QnA.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QnaService {
    @Autowired
    private DocumentRepository documentRepository;

    public List<Document> search(QnaRequest request) {
        String query = request.getQuestion();
        return documentRepository.searchDocuments(query, Pageable.ofSize(10)).getContent();
    }
}