package com.App.Spring.Boot.Docs.QnA.util;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;

import java.util.ArrayList;
import java.util.List;

public class TestDataGenerator {
    public static List<DocumentDTO> generateDocuments(int count) {
        List<DocumentDTO> documents = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DocumentDTO dto = new DocumentDTO();
            dto.setTitle("Test Doc " + i);
            dto.setContent("Sample content for document " + i);
            dto.setAuthor("Author " + i);
            dto.setType("PDF");
            documents.add(dto);
        }
        return documents;
    }
}
