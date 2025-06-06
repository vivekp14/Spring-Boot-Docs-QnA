package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentUploadRequest;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import com.App.Spring.Boot.Docs.QnA.repository.DocumentRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentServiceTest {
    @Mock
    private DocumentRepository repository;
    private DocumentService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new DocumentService(repository);
    }

    @Test
    void uploadDocument_shouldSaveAndReturnResponse() {
        DocumentUploadRequest req = new DocumentUploadRequest();
        req.setFilename("file.txt");
        req.setType("txt");
        req.setAuthor("alice");
        req.setContent("hello world");
        req.setKeywords(Set.of("hello"));

        Document doc = Document.builder()
                .id(1L)
                .filename("file.txt")
                .type("txt")
                .author("alice")
                .content("hello world")
                .keywords(Set.of("hello"))
                .build();

        when(repository.save(any(Document.class))).thenReturn(doc);

        var resp = service.uploadDocument(req);

        assertEquals("file.txt", resp.getFilename());
        verify(repository, times(1)).save(any(Document.class));
    }
}
