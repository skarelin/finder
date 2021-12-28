package com.business.finder.investment.db;

import com.business.finder.investment.domain.InvestmentProposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentProposalRepository extends JpaRepository<InvestmentProposal, Long> {
}
