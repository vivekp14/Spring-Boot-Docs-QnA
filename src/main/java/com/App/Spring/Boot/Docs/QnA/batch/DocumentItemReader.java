package com.App.Spring.Boot.Docs.QnA.batch;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentItemReader implements ItemReader<DocumentDTO> {
    private List<DocumentDTO> documents = new ArrayList<>();
    private int index = 0;

    public void setDocuments(List<DocumentDTO> documents) {
        this.documents = documents != null ? new ArrayList<>(documents) : new ArrayList<>();
        this.index = 0;
    }

    @Override
    public DocumentDTO read() {
        if (index < documents.size()) {
            return documents.get(index++);
        }
        return null;
    }
}
