package com.App.Spring.Boot.Docs.QnA.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entity representing a document with metadata and full-text search capabilities.
 */
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String author;
    private String type;
    private LocalDateTime createdAt;
    @ElementCollection
    @CollectionTable(name = "document_keywords", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "keyword")
    private Set<String> keywords;
    @Column(columnDefinition = "tsvector")
    private String searchVector;

    // Getters, setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Set<String> getKeywords() { return keywords; }
    public void setKeywords(Set<String> keywords) { this.keywords = keywords; }
    public String getSearchVector() { return searchVector; }
    public void setSearchVector(String searchVector) { this.searchVector = searchVector; }
}