package com.App.Spring.Boot.Docs.QnA.dto;
import java.time.LocalDateTime;

public class DocumentDTO {
    private String title;
    private String content;
    private String author;
    private String type;
    private LocalDateTime createdAt;

    // Getters, setters, and constructors
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
}
