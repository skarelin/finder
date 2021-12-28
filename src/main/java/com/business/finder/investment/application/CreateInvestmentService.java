package com.business.finder.investment.application;

import com.business.finder.investment.application.port.CreateInvestmentProposalUseCase;
import com.business.finder.investment.application.port.CreateInvestmentProposalUseCase.Error;
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
    public ResponseEntity<CreateInvestmentProposalResponse> create(CreateInvestmentProposalData data) {
        List<Error> errors = new ArrayList<>();
        if (data.getMinimumInvestmentValue() > 0 && data.getProjectBudget() == 0){
            errors.add(Error.PROJECT_BUDGET_SHOULD_BE_PRESENT);
        }
        repository.save(data.toInvestmentProposal());
        if(errors.isEmpty()){
           return ResponseEntity.ok(CreateInvestmentProposalResponse.ok);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CreateInvestmentProposalResponse.errors(errors));
        }
    }
}
