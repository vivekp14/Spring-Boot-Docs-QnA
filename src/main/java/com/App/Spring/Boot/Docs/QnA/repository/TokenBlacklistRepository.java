package com.App.Spring.Boot.Docs.QnA.repository;
import com.App.Spring.Boot.Docs.QnA.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, UUID> {
    Optional<TokenBlacklist> findByToken(String token);
}


