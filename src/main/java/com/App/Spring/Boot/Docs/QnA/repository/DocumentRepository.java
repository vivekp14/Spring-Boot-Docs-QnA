package com.App.Spring.Boot.Docs.QnA.repository;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository for Document entity with full-text search support.
 */
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("SELECT d FROM Document d WHERE d.author = :author OR d.type = :type")
    Page<Document> findByMetadata(String author, String type, Pageable pageable);

    @Query(value = "SELECT * FROM documents WHERE search_vector @@ to_tsquery(:query)", nativeQuery = true)
    Page<Document> searchDocuments(String query, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE documents SET search_vector = to_tsvector('english', title || ' ' || content || ' ' || array_to_string(keywords, ' '))", nativeQuery = true)
    void updateSearchVectors();
}
