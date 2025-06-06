package com.App.Spring.Boot.Docs.QnA.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private String type;
    private String author;
    private LocalDateTime uploadDate;
    private String summary;

    @Lob
    private String content;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="document_keywords", joinColumns=@JoinColumn(name="doc_id"))
    @Column(name="keyword")
    private Set<String> keywords;
}
