package com.business.finder.investment.db;

import com.business.finder.investment.domain.InvestmentProposal;
import com.business.finder.metadata.ProposalState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Optional;

public interface InvestmentProposalRepository extends JpaRepository<InvestmentProposal, Long> {
    Optional<InvestmentProposal> findByUuid(String investmentProposalUuid);

    Page<InvestmentProposal> findByStateEquals (ProposalState state, Pageable pageable);
}
