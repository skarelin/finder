package com.business.finder.investment.application.port;

import com.business.finder.investment.domain.InvestmentProposal;
import com.business.finder.metadata.Country;
import com.business.finder.metadata.Industry;
import com.business.finder.metadata.Language;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface CreateInvestmentProposalUseCase {

    ResponseEntity<CreateInvestmentProposalResponse> create(CreateInvestmentProposalCommand command);

    @Value
    @Builder
    class CreateInvestmentProposalCommand {
        String projectSubject;
        String projectDescription;
        Country country;
        String city;
        Industry industry;
        int minimumInvestmentValue;
        Language teamLanguage;
        int projectBudget;
        int expectedPaybackPeriod;
        public InvestmentProposal toInvestmentProposal(){
            return new InvestmentProposal(this.projectSubject,
                    this.projectDescription,
                    this.country,
                    this.city,
                    this.industry,
                    this.minimumInvestmentValue,
                    this.teamLanguage,
                    this.projectBudget,
                    this.expectedPaybackPeriod);
        }
    }

    @Value
    class CreateInvestmentProposalResponse {

        public static CreateInvestmentProposalResponse ok = new CreateInvestmentProposalResponse(true, Collections.emptyList());

        public static CreateInvestmentProposalResponse errors(List<Error> errors){
            return new CreateInvestmentProposalResponse(false, errors);
        }

        static CreateInvestmentProposalResponse errors(Error... error){
            return new CreateInvestmentProposalResponse(false, Arrays.asList(error));
        }

        boolean result;
        List<Error> errors;
    }


    @Getter
    enum Error{
        PROJECT_BUDGET_SHOULD_BE_PRESENT
    }

}
