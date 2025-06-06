package com.App.Spring.Boot.Docs.QnA.repository;

import com.App.Spring.Boot.Docs.QnA.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
