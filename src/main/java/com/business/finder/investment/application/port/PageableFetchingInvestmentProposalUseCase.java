package com.business.finder.investment.application.port;

import com.business.finder.investment.application.dto.InvestmentProposalDataResponse;
import com.business.finder.metadata.ProposalState;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public interface PageableFetchingInvestmentProposalUseCase {

    Page<InvestmentProposalDataResponse> fetch(Pageable pageable, Long userId);

    Page<InvestmentProposalDataResponse> fetchByProposalsState(FetchByProposalStateCommand command);

    @Value
    class FetchByProposalStateCommand {
        Pageable pageable;
        Long userId;
        @Enumerated(EnumType.STRING)
        ProposalState proposalState;
    }
}
