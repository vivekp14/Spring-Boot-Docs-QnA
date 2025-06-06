package com.App.Spring.Boot.Docs.QnA.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class DocumentUploadRequest {
    private String filename;
    private String type;
    private String author;
    private LocalDateTime uploadDate;
    private String content;
    private Set<String> keywords;
}
