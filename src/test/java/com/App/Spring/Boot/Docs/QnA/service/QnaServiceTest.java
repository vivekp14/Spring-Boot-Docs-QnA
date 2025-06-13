package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import com.App.Spring.Boot.Docs.QnA.exception.ApiException;
import com.App.Spring.Boot.Docs.QnA.repository.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class QnaServiceTest {
    @Autowired
    private QnaService qnaService;

    @MockBean
    private DocumentRepository documentRepository;

    private Document document;

    @BeforeEach
    void setUp() {
        document = new Document();
        document.setTitle("Test Document");
        document.setContent("Test content");
    }

    @Test
    void testAnswerQuestionSuccess() {
        Page<Document> page = new PageImpl<>(Collections.singletonList(document));
        when(documentRepository.searchDocuments(anyString(), any())).thenReturn(page);
        Page<DocumentDTO> result = qnaService.answerQuestion("Test query", PageRequest.of(0, 10));
        assertFalse(result.getContent().isEmpty());
        assertEquals("Test Document", result.getContent().get(0).getTitle());
    }

    @Test
    void testAnswerQuestionEmptyQuery() {
        assertThrows(ApiException.class, () -> qnaService.answerQuestion("", PageRequest.of(0, 10)));
    }
}
