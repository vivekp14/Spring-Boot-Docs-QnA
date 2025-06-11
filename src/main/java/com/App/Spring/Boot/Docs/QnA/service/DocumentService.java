package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.config.RabbitMQConfig;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import com.App.Spring.Boot.Docs.QnA.exception.DocumentProcessingException;
import com.App.Spring.Boot.Docs.QnA.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for document ingestion and retrieval.
 */
@Service
public class DocumentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentService.class);

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Initiates document ingestion by sending to RabbitMQ queue.
     */
    public void ingestDocument(DocumentDTO dto) {
        LOGGER.info("Queueing document for ingestion: {}", dto.getTitle());
        rabbitTemplate.convertAndSend(RabbitMQConfig.DOCUMENT_QUEUE, dto);
    }

    /**
     * Processes a single document with retry logic.
     */
    @Async
    @Transactional
    @Retryable(value = DocumentProcessingException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public void processDocument(DocumentDTO dto) {
        try {
            LOGGER.debug("Processing document: {}", dto.getTitle());
            Document document = new Document();
            document.setTitle(dto.getTitle());
            document.setContent(dto.getContent());
            document.setAuthor(dto.getAuthor());
            document.setType(dto.getType());
            document.setKeywords(dto.getKeywords());
            document.setCreatedAt(LocalDateTime.now());
            documentRepository.save(document);
            // Update tsvector after saving
            documentRepository.updateSearchVectors();
        } catch (Exception e) {
            LOGGER.error("Failed to process document: {}", dto.getTitle(), e);
            throw new DocumentProcessingException("Failed to process document: " + dto.getTitle(), e);
        }
    }

    public List<Document> getDocuments(String author, String type, int page, int size) {
        LOGGER.debug("Fetching documents for author: {}, type: {}", author, type);
        return documentRepository.findByMetadata(author, type, Pageable.ofSize(size).withPage(page)).getContent();
    }
}