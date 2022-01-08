package com.business.finder.partnership.application.port;

import com.business.finder.investment.application.port.PageableFetchingInvestmentProposalUseCase;
import com.business.finder.metadata.ProposalState;
import com.business.finder.partnership.application.PartnershipProposalResponse;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public interface PageableFetchingPartnershipProposalUseCase {

    Page<PartnershipProposalResponse> fetch(Pageable pageable);

    Page<PartnershipProposalResponse> fetchByProposalsState(FetchByProposalStateCommand command);

    @Value
    class FetchByProposalStateCommand {
        Pageable pageable;
        Long userId;
        @Enumerated(EnumType.STRING)
        ProposalState proposalState;
    }

}
