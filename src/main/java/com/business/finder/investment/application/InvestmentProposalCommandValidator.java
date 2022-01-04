package com.business.finder.investment.application;

import com.business.finder.investment.application.port.QueryInvestmentProposalUseCase;
import com.business.finder.investment.application.port.QueryInvestmentProposalUseCase.Error;
import com.business.finder.investment.application.port.QueryInvestmentProposalUseCase.CreateInvestmentProposalCommand;
import com.business.finder.investment.application.port.QueryInvestmentProposalUseCase.UpdateInvestmentProposalCommand;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase.Error.USER_IS_NOT_PRESENT;

@Component
class InvestmentProposalCommandValidator {

    public List<Error> validateCreateInvestment(CreateInvestmentProposalCommand command){
        List<Error> errors = new ArrayList<>();
        if (command.getMinimumInvestmentValue() > 0 && command.getProjectBudget() == 0){
            errors.add(Error.PROJECT_BUDGET_SHOULD_BE_PRESENT);
        }
        return errors;
    }

    public List<Error> validateUpdateInvestment(UpdateInvestmentProposalCommand command){
        List<Error> errors = new ArrayList<>();
        if (command.getMinimumInvestmentValue() > 0 && command.getProjectBudget() == 0){
            errors.add(Error.PROJECT_BUDGET_SHOULD_BE_PRESENT);
        }
        return errors;
    }

}
