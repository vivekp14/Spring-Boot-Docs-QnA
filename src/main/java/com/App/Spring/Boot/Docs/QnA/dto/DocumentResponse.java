package com.App.Spring.Boot.Docs.QnA.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class DocumentResponse {
    private Long id;
    private String filename;
    private String type;
    private String author;
    private LocalDateTime uploadDate;
    private String summary;
    private Set<String> keywords;
}
