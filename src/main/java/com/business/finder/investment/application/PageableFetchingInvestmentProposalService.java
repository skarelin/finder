package com.business.finder.investment.application;

import com.business.finder.investment.application.dto.InvestmentProposalDataResponse;
import com.business.finder.investment.application.mapper.InvestmentProposalMapper;
import com.business.finder.investment.application.port.PageableFetchingInvestmentProposalUseCase;
import com.business.finder.investment.db.InvestmentProposalRepository;
import com.business.finder.investment.domain.InvestmentProposal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageableFetchingInvestmentProposalService implements PageableFetchingInvestmentProposalUseCase {

    private final InvestmentProposalRepository repository;
    private final InvestmentProposalMapper mapper;

//    TODO this can return nothing, if doesn't exist any proposals. Must return some error;
    @Override
    public Page<InvestmentProposalDataResponse> fetch(Pageable pageable, Long userId) {
        return repository
                .findAll(pageable)
                .map(investmentProposal -> createResponse(investmentProposal, userId));
    }

    @Override
    public Page<InvestmentProposalDataResponse> fetchByProposalsState(FetchByProposalStateCommand command) {
        return repository
                .findByStateEquals(command.getProposalState(), command.getPageable())
                .map(investmentProposal -> createResponse(investmentProposal, command.getUserId()));
    }


    private InvestmentProposalDataResponse createResponse(InvestmentProposal investmentProposal, Long userId) {
        return mapper.toInvestmentProposalDataResponse(investmentProposal, isAdvertOwner(investmentProposal.getUserId(), userId));
    }

    private boolean isAdvertOwner(Long userIdOwnerProposal, Long currentUserId) {
        return userIdOwnerProposal.equals(currentUserId);
    }
}
