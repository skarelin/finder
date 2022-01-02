package com.business.finder.user.db;

import com.business.finder.user.domain.BfProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BfProfilePictureRepository extends JpaRepository<BfProfilePicture, Long> {
}
