package com.business.finder.investment.application;

import com.business.finder.investment.application.dto.InvestmentProposalDataResponse;
import com.business.finder.investment.application.exceptions.InvestmentProposalNotFoundException;
import com.business.finder.investment.application.exceptions.NoAccessToInvestmentProposalException;
import com.business.finder.investment.application.mapper.InvestmentProposalMapper;
import com.business.finder.investment.application.port.QueryInvestmentProposalUseCase;
import com.business.finder.investment.db.InvestmentProposalRepository;
import com.business.finder.investment.domain.InvestmentProposal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QueryInvestmentProposalService implements QueryInvestmentProposalUseCase {

    private final InvestmentProposalRepository repository;

    private final InvestmentProposalCommandValidator validator;

    private final InvestmentProposalMapper mapper;

    @Override
    public ResponseEntity<InvestmentProposalResponse> create(CreateInvestmentProposalCommand command) {
        List<Error> errors = validator.validateCreateInvestment(command);
        if (errors.isEmpty()) {
            repository.save(command.toInvestmentProposal());
            return ResponseEntity.ok(InvestmentProposalResponse.ok);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(InvestmentProposalResponse.errors(errors));
        }
    }

    @Override
    public ResponseEntity<InvestmentProposalResponse> update(UpdateInvestmentProposalCommand command) {
        List<Error> errors = validator.validateUpdateInvestment(command);
        if (errors.isEmpty()) {
            return repository.findByUuid(command.getInvestmentProposalUuid())
                    .map(investmentProposal -> authorize(investmentProposal, command.getUserId()))
                    .map(investmentProposal -> {
                        updateDate(investmentProposal, command);
                        repository.save(investmentProposal);
                        return ResponseEntity.ok(InvestmentProposalResponse.ok);
                    })
                    .orElseThrow(() -> new InvestmentProposalNotFoundException("Investment proposal is not found during updating request. UUID:" + command.getInvestmentProposalUuid()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(InvestmentProposalResponse.errors(errors));
        }
    }

    @Override
    public ResponseEntity<InvestmentProposalDataResponse> findByUuid(String investmentProposalUuid, Long userId) {
        return ResponseEntity.ok(repository.findByUuid(investmentProposalUuid)
                .map(investmentProposal -> createGetResponse(investmentProposal, userId))
                .orElseThrow(() -> new InvestmentProposalNotFoundException("Not found Investment proposal with investmentProposalUuid: " + investmentProposalUuid)));

    }

    @Override
    public ResponseEntity<InvestmentProposalResponse> delete(String investmentProposalUuid, Long userId) {
        InvestmentProposal proposal = repository
                .findByUuid(investmentProposalUuid)
                .map(investmentProposal -> authorize(investmentProposal, userId))
                .orElseThrow(() -> new InvestmentProposalNotFoundException("Not found Investment proposal with investmentProposalUuid: " + investmentProposalUuid));
        repository.delete(proposal);
        return ResponseEntity.ok(InvestmentProposalResponse.ok);
    }

    @Override
    public Page<InvestmentProposalDataResponse> fetch(Pageable pageable, Long userId) {
        return repository
                .findAll(pageable)
                .map(investmentProposal -> createGetResponse(investmentProposal, userId));
    }

    private void updateDate(InvestmentProposal investmentProposal, UpdateInvestmentProposalCommand command) {
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

    private InvestmentProposalDataResponse createGetResponse(InvestmentProposal investmentProposal, Long userId) {
        GetInvestmentProposalCommand getInvestmentProposalCommand = GetInvestmentProposalCommand.fromInvestmentProposal(investmentProposal);
        getInvestmentProposalCommand.setOwner(isAdvertOwner(getInvestmentProposalCommand.getUserId(), userId));
        return mapper.toInvestmentProposalDataResponse(getInvestmentProposalCommand);
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
