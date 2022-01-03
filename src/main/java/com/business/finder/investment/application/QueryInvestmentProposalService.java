package com.business.finder.investment.application;

import com.business.finder.investment.application.dto.InvestmentProposalDataResponse;
import com.business.finder.investment.application.exceptions.InvestmentProposalNotFoundException;
import com.business.finder.investment.application.exceptions.NoAccessToInvestmentProposalException;
import com.business.finder.investment.application.mapper.InvestmentProposalMapper;
import com.business.finder.investment.application.port.QueryInvestmentProposalUseCase;
import com.business.finder.investment.db.InvestmentProposalRepository;
import com.business.finder.investment.domain.InvestmentProposal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class QueryInvestmentProposalService implements QueryInvestmentProposalUseCase {

    private final InvestmentProposalRepository repository;
    private final InvestmentProposalCommandValidator validator;
    private final InvestmentProposalMapper mapper;

    @Override
    public InvestmentProposalResponse create(CreateInvestmentProposalCommand command) {
        List<Error> errors = validator.validateCreateInvestment(command);
        if (errors.isEmpty()) {
            InvestmentProposal savedProposal = command.toInvestmentProposal();
            repository.save(savedProposal);
            log.info("Created investment proposal " + savedProposal.getUuid() + " by user " + command.getUserId());
            return InvestmentProposalResponse.OK;
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
                        log.info("Updated investment proposal " + investmentProposal.getUuid() + " by user " + command.getUserId());
                        return InvestmentProposalResponse.OK;
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

    public InvestmentProposalResponse remove(RemoveInvestmentProposalCommand command) {
        InvestmentProposal proposal = repository
                .findByUuid(command.getInvestmentProposalUuid())
                .map(investmentProposal -> authorize(investmentProposal, command.getCurrentUserId()))
                .orElseThrow(() -> new InvestmentProposalNotFoundException("Not found Investment proposal with investmentProposalUuid: " + command.getInvestmentProposalUuid()));
        log.info("Removing investment proposal " + proposal.getUuid() + " by user " + command.getCurrentUserId());
        repository.delete(proposal);
        return InvestmentProposalResponse.OK;
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
            log.error("User " + userId + " tried to get access for not his proposal " + investmentProposal.getUuid());
            throw new NoAccessToInvestmentProposalException("Current user doesn't have permission to partnership proposal " + investmentProposal.getUuid());
        }
        return investmentProposal;
    }
}
