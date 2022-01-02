package com.business.finder.investment.application.mapper;

import com.business.finder.investment.application.dto.InvestmentProposalDataResponse;
import com.business.finder.investment.application.port.QueryInvestmentProposalUseCase.GetInvestmentProposalCommand;
import com.business.finder.investment.domain.InvestmentProposal;
import org.springframework.stereotype.Component;

@Component
public class InvestmentProposalMapper {

    public InvestmentProposalDataResponse toInvestmentProposalDataResponse(InvestmentProposal investmentProposal, boolean isOwner) {
        return InvestmentProposalDataResponse.builder()
                .projectSubject(investmentProposal.getProjectSubject())
                .projectDescription(investmentProposal.getProjectDescription())
                .country(investmentProposal.getCountry())
                .city(investmentProposal.getCity())
                .industry(investmentProposal.getIndustry())
                .teamLanguage(investmentProposal.getTeamLanguage())
                .minimumInvestmentValue(investmentProposal.getMinimumInvestmentValue())
                .projectBudget(investmentProposal.getProjectBudget())
                .expectedPaybackPeriod(investmentProposal.getExpectedPaybackPeriod())
                .investmentProposalUuid(investmentProposal.getUuid())
                .isOwner(isOwner)
                .build();
    }


}
