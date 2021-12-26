package com.business.finder.user.db;

import com.business.finder.user.domain.BfArchiveUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BfArchiveUserRepository extends JpaRepository<BfArchiveUser, Long> {
}
