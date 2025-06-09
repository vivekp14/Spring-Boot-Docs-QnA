package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import com.App.Spring.Boot.Docs.QnA.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {
    @InjectMocks
    private DocumentService documentService;
    @Mock
    private DocumentRepository documentRepository;

    @Test
    public void testIngestDocument() {
        DocumentDTO dto = new DocumentDTO();
        dto.setTitle("Test Doc");
        dto.setContent("Content");
        dto.setAuthor("Author");
        dto.setType("PDF");
        documentService.ingestDocument(dto);
        verify(documentRepository, times(1)).save(any(Document.class));
    }
}

