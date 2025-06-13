package com.App.Spring.Boot.Docs.QnA.controller;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.service.DocumentIngestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DocumentIngestionController.class)
class DocumentIngestionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentIngestionService ingestionService;

    @Autowired
    private ObjectMapper objectMapper;

    private DocumentDTO documentDTO;

    @BeforeEach
    void setUp() {
        documentDTO = new DocumentDTO();
        documentDTO.setTitle("Test Document");
        documentDTO.setContent("Test content");
    }

    @Test
    void testIngestSingleDocument() throws Exception {
        mockMvc.perform(post("/api/v1/documents/ingest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(documentDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Document ingestion queued"));
        verify(ingestionService).ingestDocument(any(DocumentDTO.class));
    }

    @Test
    void testIngestBatchDocuments() throws Exception {
        mockMvc.perform(post("/api/v1/documents/ingest/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singletonList(documentDTO))))
                .andExpect(status().isOk())
                .andExpect(content().string("Batch ingestion job started"));
        verify(ingestionService).ingestBatchDocuments(anyList());
    }
}
