package com.business.finder.investment.application;

import com.business.finder.investment.application.port.CreateInvestmentProposalUseCase;
import com.business.finder.investment.db.InvestmentProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CreateInvestmentService  implements CreateInvestmentProposalUseCase {

    private final InvestmentProposalRepository repository;

    @Override
    public ResponseEntity<CreateInvestmentProposalResponse> create(CreateInvestmentProposalCommand command) {
        List<Error> errors = new ArrayList<>();
        if (command.getMinimumInvestmentValue() > 0 && command.getProjectBudget() == 0){
            errors.add(Error.PROJECT_BUDGET_SHOULD_BE_PRESENT);
        }
        repository.save(command.toInvestmentProposal());
        if(errors.isEmpty()){
           return ResponseEntity.ok(CreateInvestmentProposalResponse.ok);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CreateInvestmentProposalResponse.errors(errors));
        }
    }
}
