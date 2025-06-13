package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.batch.DocumentItemReader;
import com.App.Spring.Boot.Docs.QnA.config.RabbitMQConfig;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import com.App.Spring.Boot.Docs.QnA.exception.ApiException;
import com.App.Spring.Boot.Docs.QnA.repository.DocumentRepository;
import com.App.Spring.Boot.Docs.QnA.utils.DocumentExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentIngestionService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentIngestionService.class);

    private final RabbitTemplate rabbitTemplate;
    private final JobLauncher jobLauncher;
    private final Job documentIngestionJob;
    private final DocumentRepository documentRepository;
    private final DocumentExtractor documentExtractor;
    private final DocumentItemReader documentItemReader;

    public DocumentIngestionService(RabbitTemplate rabbitTemplate, JobLauncher jobLauncher,
                                    Job documentIngestionJob, DocumentRepository documentRepository,
                                    DocumentExtractor documentExtractor, DocumentItemReader documentItemReader) {
        this.rabbitTemplate = rabbitTemplate;
        this.jobLauncher = jobLauncher;
        this.documentIngestionJob = documentIngestionJob;
        this.documentRepository = documentRepository;
        this.documentExtractor = documentExtractor;
        this.documentItemReader = documentItemReader;
    }

    public void ingestDocument(DocumentDTO documentDTO) {
        logger.info("Queueing document ingestion: {}", documentDTO.getTitle());
        rabbitTemplate.convertAndSend(RabbitMQConfig.DOCUMENT_QUEUE, documentDTO);
    }

    public void ingestBatchDocuments(List<DocumentDTO> documents) {
        if (documents == null || documents.isEmpty()) {
            throw new ApiException("Document list cannot be empty", 400);
        }
        logger.info("Starting batch ingestion for {} documents", documents.size());
        try {
            documentItemReader.setDocuments(documents);
            JobParametersBuilder paramsBuilder = new JobParametersBuilder();
            paramsBuilder.addLong("startAt", System.currentTimeMillis());
            jobLauncher.run(documentIngestionJob, paramsBuilder.toJobParameters());
        } catch (Exception e) {
            logger.error("Batch ingestion failed: {}", e.getMessage());
            throw new ApiException("Failed to start batch ingestion: " + e.getMessage(), 500);
        }
    }

    public void ingestFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ApiException("File cannot be empty", 400);
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.matches("application/pdf|application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            throw new ApiException("Unsupported file type. Only PDF and DOCX are allowed", 400);
        }
        try {
            DocumentDTO documentDTO = new DocumentDTO();
            documentDTO.setTitle(file.getOriginalFilename());
            documentDTO.setContent(documentExtractor.extractContent(file));
            documentDTO.setType(contentType.contains("pdf") ? "PDF" : "DOCX");
            ingestDocument(documentDTO);
        } catch (Exception e) {
            logger.error("File ingestion failed: {}", e.getMessage());
            throw new ApiException("Failed to process file: " + e.getMessage(), 500);
        }
    }

    public void processDocument(DocumentDTO dto) {
        Document document = new Document();
        document.setTitle(dto.getTitle());
        document.setContent(dto.getContent());
        document.setAuthor(dto.getAuthor());
        document.setType(dto.getType());
        document.setKeywords(dto.getKeywords());
        document.setCreatedAt(LocalDateTime.now());
        documentRepository.save(document);
    }
}
