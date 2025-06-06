package com.App.Spring.Boot.Docs.QnA.repository;

import com.App.Spring.Boot.Docs.QnA.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Page<Document> findByAuthor(String author, Pageable pageable);
    Page<Document> findByType(String type, Pageable pageable);
    Page<Document> findByAuthorAndType(String author, String type, Pageable pageable);
    Page<Document> findByContentContainingIgnoreCase(String keyword, Pageable pageable);
}
