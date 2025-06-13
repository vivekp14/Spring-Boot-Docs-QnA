package com.App.Spring.Boot.Docs.QnA.batch;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import org.springframework.batch.item.ItemProcessor;
import java.time.LocalDateTime;

public class DocumentBatchProcessor implements ItemProcessor<DocumentDTO, Document> {
    @Override
    public Document process(DocumentDTO dto) {
        Document doc = new Document();
        doc.setTitle(dto.getTitle());
        doc.setContent(dto.getContent());
        doc.setAuthor(dto.getAuthor());
        doc.setType(dto.getType());
        doc.setKeywords(dto.getKeywords());
        doc.setCreatedAt(LocalDateTime.now());
        return doc;
    }
}
