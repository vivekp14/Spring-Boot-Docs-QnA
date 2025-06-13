package com.App.Spring.Boot.Docs.QnA.repository;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("SELECT d FROM Document d WHERE (:author IS NULL OR d.author = :author) AND (:type IS NULL OR d.type = :type)")
    Page<Document> findByMetadata(@Param("author") String author, @Param("type") String type, Pageable pageable);

    @Query("SELECT d FROM Document d WHERE to_tsquery('english', :query) @@ d.searchVector")
    Page<Document> searchDocuments(@Param("query") String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE documents SET search_vector = to_tsvector('english', " +
            "COALESCE(title, '') || ' ' || " +
            "COALESCE(content, '') || ' ' || " +
            "COALESCE(author, '') || ' ' || " +
            "COALESCE(array_to_string(keywords, ' '), '')) WHERE id = :id", nativeQuery = true)
    void updateSearchVectors();
}

