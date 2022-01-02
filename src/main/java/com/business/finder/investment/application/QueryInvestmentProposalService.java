package com.business.finder.investment.application;

import com.business.finder.investment.application.dto.InvestmentProposalDataResponse;
import com.business.finder.investment.application.exceptions.InvestmentProposalNotFoundException;
import com.business.finder.investment.application.exceptions.NoAccessToInvestmentProposalException;
import com.business.finder.investment.application.mapper.InvestmentProposalMapper;
import com.business.finder.investment.application.port.QueryInvestmentProposalUseCase;
import com.business.finder.investment.db.InvestmentProposalRepository;
import com.business.finder.investment.domain.InvestmentProposal;
import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase;
import com.business.finder.partnership.domain.PartnershipProposal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QueryInvestmentProposalService implements QueryInvestmentProposalUseCase {

    private final InvestmentProposalRepository repository;

    private final InvestmentProposalCommandValidator validator;

    private final InvestmentProposalMapper mapper;

    @Override
    public InvestmentProposalResponse create(CreateInvestmentProposalCommand command) {
        List<Error> errors = validator.validateCreateInvestment(command);
        if (errors.isEmpty()) {
            repository.save(command.toInvestmentProposal());
            return InvestmentProposalResponse.ok;
        } else {
            return InvestmentProposalResponse.errors(errors);
        }
    }

    @Override
    @Transactional
    public InvestmentProposalResponse update(UpdateInvestmentProposalCommand command) {
        List<Error> errors = validator.validateUpdateInvestment(command);
        if (errors.isEmpty()) {
            return repository.findByUuid(command.getInvestmentProposalUuid())
                    .map(investmentProposal -> authorize(investmentProposal, command.getUserId()))
                    .map(investmentProposal -> {
                        updateData(investmentProposal, command);
                        return InvestmentProposalResponse.ok;
                    })
                    .orElseThrow(() -> new InvestmentProposalNotFoundException("Investment proposal is not found during updating request. UUID:" + command.getInvestmentProposalUuid()));
        } else {
            return InvestmentProposalResponse.errors(errors);
        }
    }

    @Override
    public Optional<InvestmentProposalDataResponse> findByUuid(String investmentProposalUuid, Long userId) {
        return repository.findByUuid(investmentProposalUuid)
                .map(investmentProposal -> createResponse(investmentProposal, userId));

    }

    @Override
    public InvestmentProposalResponse delete(String investmentProposalUuid, Long userId) {
        InvestmentProposal proposal = repository
                .findByUuid(investmentProposalUuid)
                .map(investmentProposal -> authorize(investmentProposal, userId))
                .orElseThrow(() -> new InvestmentProposalNotFoundException("Not found Investment proposal with investmentProposalUuid: " + investmentProposalUuid));
        repository.delete(proposal);
        return InvestmentProposalResponse.ok;
    }

    @Override
    public Page<InvestmentProposalDataResponse> fetchProposalsPageable(Pageable pageable, Long userId) {
        return repository
                .findAll(pageable)
                .map(investmentProposal -> createResponse(investmentProposal, userId));
    }

    private void updateData(InvestmentProposal investmentProposal, UpdateInvestmentProposalCommand command) {
        investmentProposal.setProjectSubject(command.getProjectSubject());
        investmentProposal.setProjectDescription(command.getProjectDescription());
        investmentProposal.setCountry(command.getCountry());
        investmentProposal.setCity(command.getCity());
        investmentProposal.setIndustry(command.getIndustry());
        investmentProposal.setTeamLanguage(command.getTeamLanguage());
        investmentProposal.setMinimumInvestmentValue(command.getMinimumInvestmentValue());
        investmentProposal.setProjectBudget(command.getProjectBudget());
        investmentProposal.setExpectedPaybackPeriod(command.getExpectedPaybackPeriod());
    }

    private InvestmentProposalDataResponse createResponse(InvestmentProposal investmentProposal, Long userId) {
        return mapper.toInvestmentProposalDataResponse(investmentProposal, isAdvertOwner(investmentProposal.getUserId(), userId));
    }

    private boolean isAdvertOwner(Long userIdOwnerProposal, Long currentUserId) {
        return userIdOwnerProposal.equals(currentUserId);
    }

    private InvestmentProposal authorize(InvestmentProposal investmentProposal, Long userId) {
        if (!investmentProposal.getUserId().equals(userId)) {
            throw new NoAccessToInvestmentProposalException("Current user doesn't have permission to partnership proposal " + investmentProposal.getUuid());
        }
        return investmentProposal;
    }
}
