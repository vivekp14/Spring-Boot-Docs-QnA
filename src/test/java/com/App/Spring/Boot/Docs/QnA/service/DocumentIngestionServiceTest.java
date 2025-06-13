package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.batch.DocumentItemReader;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.repository.DocumentRepository;
import com.App.Spring.Boot.Docs.QnA.utils.DocumentExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Collections;

import static org.mockito.Mockito.*;

@SpringBootTest
class DocumentIngestionServiceTest {
    @Autowired
    private DocumentIngestionService ingestionService;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private JobLauncher jobLauncher;

    @MockBean
    private Job documentIngestionJob;

    @MockBean
    private DocumentRepository documentRepository;

    @MockBean
    private DocumentExtractor documentExtractor;

    @MockBean
    private DocumentItemReader documentItemReader;

    private DocumentDTO documentDTO;

    @BeforeEach
    void setUp() {
        documentDTO = new DocumentDTO();
        documentDTO.setTitle("Test Document");
        documentDTO.setContent("Test content");
    }

    @Test
    void testIngestDocument() {
        ingestionService.ingestDocument(documentDTO);
        verify(rabbitTemplate).convertAndSend(anyString(), eq(documentDTO));
    }

    @Test
    void testIngestBatchDocuments() throws Exception {
        ingestionService.ingestBatchDocuments(Collections.singletonList(documentDTO));
        verify(documentItemReader).setDocuments(anyList());
        verify(jobLauncher).run(eq(documentIngestionJob), any(JobParameters.class));
    }

    @Test
    void testIngestFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "content".getBytes());
        when(documentExtractor.extractContent(file)).thenReturn("Extracted content");
        ingestionService.ingestFile(file);
        verify(rabbitTemplate).convertAndSend(anyString(), any(DocumentDTO.class));
    }
}

