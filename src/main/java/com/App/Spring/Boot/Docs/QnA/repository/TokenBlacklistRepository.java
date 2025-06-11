package com.App.Spring.Boot.Docs.QnA.repository;
import com.App.Spring.Boot.Docs.QnA.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for TokenBlacklist entity operations.
 */
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
    boolean existsByToken(String token);
}

