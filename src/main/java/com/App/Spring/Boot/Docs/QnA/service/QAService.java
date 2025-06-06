package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.dto.QAResponse;
import com.App.Spring.Boot.Docs.QnA.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QAService {
    private final DocumentRepository documentRepository;
    private static final Logger logger = LoggerFactory.getLogger(QAService.class);

    public QAResponse answerQuestion(String question) {
        logger.info("Executing keyword search for: {}", question);
        // Return top 10 matching snippets
        var page = documentRepository.findByContentContainingIgnoreCase(question,
                PageRequest.of(0, 10));
        List<String> snippets = page.getContent().stream()
                .map(doc -> doc.getSummary() != null ? doc.getSummary() :
                        doc.getContent().substring(0, Math.min(200, doc.getContent().length())))
                .collect(Collectors.toList());
        QAResponse response = new QAResponse();
        response.setMatchingSnippets(snippets);
        return response;
    }
}
