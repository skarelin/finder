package com.business.finder.partnership.db;

import com.business.finder.partnership.domain.PartnershipProposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnershipProposalRepository extends JpaRepository<PartnershipProposal, Long> {
    Optional<PartnershipProposal> findByUuid(String uuid);
}
