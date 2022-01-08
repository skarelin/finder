package com.business.finder.investment.application.port;

import com.business.finder.investment.application.dto.InvestmentProposalDataResponse;
import com.business.finder.investment.domain.InvestmentProposal;
import com.business.finder.metadata.Country;
import com.business.finder.metadata.Industry;
import com.business.finder.metadata.Language;
import com.business.finder.metadata.ProposalState;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface QueryInvestmentProposalUseCase {

    InvestmentProposalResponse create(CreateInvestmentProposalCommand command);

    InvestmentProposalResponse update(UpdateInvestmentProposalCommand command);

    Optional<InvestmentProposalDataResponse> findByUuid(String investmentProposalUuid, Long userId);

    InvestmentProposalResponse remove(RemoveInvestmentProposalCommand command);





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
        @NotNull Long userId;
        public InvestmentProposal toInvestmentProposal(){
            return new InvestmentProposal(this.projectSubject,
                    this.projectDescription,
                    this.country,
                    this.city,
                    this.industry,
                    this.minimumInvestmentValue,
                    this.teamLanguage,
                    this.projectBudget,
                    this.expectedPaybackPeriod,
                    this.userId,
                    ProposalState.NEW);
        }
    }

    @Value
    @Builder
    class UpdateInvestmentProposalCommand {
        @NotNull String investmentProposalUuid;
        String projectSubject;
        String projectDescription;
        Country country;
        String city;
        Industry industry;
        int minimumInvestmentValue;
        Language teamLanguage;
        int projectBudget;
        int expectedPaybackPeriod;
        Long userId;
    }

    @Value
    class RemoveInvestmentProposalCommand {
        @NotNull String investmentProposalUuid;
        @NotNull Long currentUserId;
    }

    @Data
    @Builder
    class GetInvestmentProposalCommand {
        String projectSubject;
        String projectDescription;
        Country country;
        String city;
        Industry industry;
        int minimumInvestmentValue;
        Language teamLanguage;
        int projectBudget;
        int expectedPaybackPeriod;
        Long userId;
        String investmentProposalUuid;
        boolean isOwner;
        public static GetInvestmentProposalCommand fromInvestmentProposal(InvestmentProposal investmentProposal){
            return GetInvestmentProposalCommand.builder()
                    .projectSubject(investmentProposal.getProjectSubject())
                    .projectDescription(investmentProposal.getProjectDescription())
                    .country(investmentProposal.getCountry())
                    .city(investmentProposal.getCity())
                    .industry(investmentProposal.getIndustry())
                    .minimumInvestmentValue(investmentProposal.getMinimumInvestmentValue())
                    .teamLanguage(investmentProposal.getTeamLanguage())
                    .projectBudget(investmentProposal.getProjectBudget())
                    .expectedPaybackPeriod(investmentProposal.getExpectedPaybackPeriod())
                    .investmentProposalUuid(investmentProposal.getUuid())
                    .userId(investmentProposal.getUserId())
                    .isOwner(false)
                    .build();
        }
    }

    @Value
    class InvestmentProposalResponse {

        public static InvestmentProposalResponse OK = new InvestmentProposalResponse(true, Collections.emptyList());

        public static InvestmentProposalResponse errors(List<Error> errors){
            return new InvestmentProposalResponse(false, errors);
        }

        static InvestmentProposalResponse errors(Error... error){
            return new InvestmentProposalResponse(false, Arrays.asList(error));
        }

        boolean success;
        List<Error> errors;
    }


    enum Error{
        PROJECT_BUDGET_SHOULD_BE_PRESENT, USER_IS_NOT_PRESENT
    }

}
