package com.business.finder.user.db;

import com.business.finder.user.domain.BfUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BfUserRepository extends JpaRepository<BfUser, Long> {
    Optional<BfUser> findByEmailIgnoreCase(String email);
}
