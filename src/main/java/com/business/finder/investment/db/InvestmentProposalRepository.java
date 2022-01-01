package com.business.finder.investment.db;

import com.business.finder.investment.domain.InvestmentProposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvestmentProposalRepository extends JpaRepository<InvestmentProposal, Long> {
    Optional<InvestmentProposal> findByUuid(String investmentProposalUuid);
}
