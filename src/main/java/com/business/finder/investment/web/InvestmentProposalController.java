package com.business.finder.investment.web;

import com.business.finder.investment.application.port.CreateInvestmentProposalUseCase;
import com.business.finder.investment.application.port.CreateInvestmentProposalUseCase.CreateInvestmentProposalCommand;
import com.business.finder.investment.application.port.CreateInvestmentProposalUseCase.CreateInvestmentProposalResponse;
import com.business.finder.metadata.Country;
import com.business.finder.metadata.Industry;
import com.business.finder.metadata.Language;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/investment-proposal")
@RequiredArgsConstructor
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class InvestmentProposalController {

    private final CreateInvestmentProposalUseCase createInvestmentProposalUseCase;

    @PostMapping
    public ResponseEntity<CreateInvestmentProposalResponse> createInvestmentProposal(@Valid @RequestBody RestCreateInvestmentProposal data) {
        return createInvestmentProposalUseCase.create(data.toCreateInvestmentProposalData());
    }


    @Data
    static class RestCreateInvestmentProposal {
        @NotBlank
        @Size(min = 5, max = 100)
        private String projectSubject;

        @NotBlank
        private String projectDescription;

        @Enumerated(EnumType.STRING)
        @NotNull
        private Country country;

        private String city;

        @Enumerated(EnumType.STRING)
        @NotNull
        private Industry industry;

        private int minimumInvestmentValue;

        @Enumerated(EnumType.STRING)
        private Language teamLanguage;

        private int projectBudget;

        private int expectedPaybackPeriod;

        public CreateInvestmentProposalCommand toCreateInvestmentProposalData() {
            return CreateInvestmentProposalCommand.builder()
                    .projectSubject(this.projectSubject)
                    .projectDescription(this.projectDescription)
                    .country(this.country)
                    .city(this.city)
                    .industry(this.industry)
                    .minimumInvestmentValue(this.minimumInvestmentValue)
                    .teamLanguage(this.teamLanguage)
                    .projectBudget(this.projectBudget)
                    .expectedPaybackPeriod(this.expectedPaybackPeriod)
                    .build();
        }
    }


}

