package com.business.finder.investment.application.mapper;

import com.business.finder.investment.application.dto.InvestmentProposalDataResponse;
import com.business.finder.investment.application.port.QueryInvestmentProposalUseCase.GetInvestmentProposalCommand;
import org.springframework.stereotype.Component;

@Component
public class InvestmentProposalMapper {

    public InvestmentProposalDataResponse toInvestmentProposalDataResponse(GetInvestmentProposalCommand command) {
        return InvestmentProposalDataResponse.builder()
                .projectSubject(command.getProjectSubject())
                .projectDescription(command.getProjectDescription())
                .country(command.getCountry())
                .city(command.getCity())
                .industry(command.getIndustry())
                .teamLanguage(command.getTeamLanguage())
                .minimumInvestmentValue(command.getMinimumInvestmentValue())
                .projectBudget(command.getProjectBudget())
                .expectedPaybackPeriod(command.getExpectedPaybackPeriod())
                .investmentProposalUuid(command.getInvestmentProposalUuid())
                .isOwner(command.isOwner())
                .build();
    }


}
