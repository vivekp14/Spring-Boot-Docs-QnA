package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.dto.QnaRequest;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import com.App.Spring.Boot.Docs.QnA.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class QnaServiceTest {
    @InjectMocks
    private QnaService qnaService;
    @Mock
    private DocumentRepository documentRepository;

    @Test
    public void testSearch() {
        QnaRequest request = new QnaRequest();
        request.setQuestion("test query");
        Page<Document> page = mock(Page.class);
        when(documentRepository.searchDocuments(anyString(), any(Pageable.class))).thenReturn(page);
        when(page.getContent()).thenReturn(List.of(new Document()));
        List<Document> result = qnaService.search(request);
        assertFalse(result.isEmpty());
    }
}
