package com.App.Spring.Boot.Docs.QnA.batch;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Reads DocumentDTO objects for batch processing.
 * Supports dynamic setting of documents via setDocuments method.
 */
@Component
public class DocumentItemReader implements ItemReader<DocumentDTO> {
    private List<DocumentDTO> documents;
    private int index = 0;

    public DocumentItemReader() {
        // Initialize empty list to avoid NullPointerException
        this.documents = new ArrayList<>();
    }

    /**
     * Sets the list of documents to be read during batch processing.
     * Resets the index to 0 for new batch jobs.
     * @param documents List of DocumentDTO objects to process
     */
    public void setDocuments(List<DocumentDTO> documents) {
        this.documents = documents != null ? documents : new ArrayList<>();
        this.index = 0;
    }

    @Override
    public DocumentDTO read() {
        if (documents != null && index < documents.size()) {
            return documents.get(index++);
        }
        return null; // Signals end of data
    }
}
