package com.App.Spring.Boot.Docs.QnA.repository;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("SELECT d FROM Document d WHERE d.author = :author OR d.type = :type")
    Page<Document> findByMetadata(String author, String type, Pageable pageable);

    @Query(value = "SELECT * FROM documents WHERE content LIKE CONCAT('%', :query, '%')", nativeQuery = true)
    Page<Document> searchDocuments(String query, Pageable pageable);
}
