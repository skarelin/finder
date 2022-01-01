package com.business.finder.investment.web;

import com.business.finder.investment.application.dto.InvestmentProposalDataResponse;
import com.business.finder.investment.application.port.QueryInvestmentProposalUseCase;
import com.business.finder.investment.application.port.QueryInvestmentProposalUseCase.CreateInvestmentProposalCommand;
import com.business.finder.investment.application.port.QueryInvestmentProposalUseCase.InvestmentProposalResponse;
import com.business.finder.investment.application.port.QueryInvestmentProposalUseCase.UpdateInvestmentProposalCommand;
import com.business.finder.metadata.Country;
import com.business.finder.metadata.Industry;
import com.business.finder.metadata.Language;
import com.business.finder.security.UserEntityDetails;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    private final QueryInvestmentProposalUseCase queryInvestmentProposalUseCase;

    @PostMapping
    public ResponseEntity<InvestmentProposalResponse> createInvestmentProposal(@AuthenticationPrincipal UserEntityDetails userEntityDetails,  @Valid @RequestBody RestCreateInvestmentProposal data) {
        return queryInvestmentProposalUseCase.create(data.toCreateInvestmentProposalCommand(userEntityDetails.getCurrentUserId()));
    }

    @PutMapping("/{investmentProposalUuid}")
    public ResponseEntity<InvestmentProposalResponse> updateInvestmentProposal(@PathVariable  String investmentProposalUuid, @AuthenticationPrincipal UserEntityDetails userEntityDetails, @Valid @RequestBody RestUpdateInvestmentProposal data){
        return queryInvestmentProposalUseCase.update(data.toUpdateInvestmentProposalCommand(userEntityDetails.getCurrentUserId(), investmentProposalUuid));
    }

    @GetMapping("/{investmentProposalUuid}")
    public ResponseEntity<InvestmentProposalDataResponse> getInvestmentProposal(@PathVariable  String investmentProposalUuid, @AuthenticationPrincipal UserEntityDetails userEntityDetails){
        return queryInvestmentProposalUseCase.findByUuid(investmentProposalUuid, userEntityDetails.getCurrentUserId());
    }

    @GetMapping("/all")
    public Page<InvestmentProposalDataResponse> getAllInvestmentProposal(Pageable pageable, @AuthenticationPrincipal UserEntityDetails userEntityDetails){
        return queryInvestmentProposalUseCase.fetch(pageable, userEntityDetails.getCurrentUserId());
    }

    @DeleteMapping({"/{investmentProposalUuid}"})
    public ResponseEntity<InvestmentProposalResponse> deleteInvestmentProposal(@PathVariable String investmentProposalUuid, @AuthenticationPrincipal UserEntityDetails userEntityDetails){
        return queryInvestmentProposalUseCase.delete(investmentProposalUuid, userEntityDetails.getCurrentUserId());
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

        public CreateInvestmentProposalCommand toCreateInvestmentProposalCommand(long userId) {
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
                    .userId(userId)
                    .build();
        }
    }

    @Data
    static class RestUpdateInvestmentProposal {
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

        public UpdateInvestmentProposalCommand toUpdateInvestmentProposalCommand(long userId, @NotNull String investmentProposalUuid) {
            return UpdateInvestmentProposalCommand.builder()
                    .projectSubject(this.projectSubject)
                    .projectDescription(this.projectDescription)
                    .country(this.country)
                    .city(this.city)
                    .industry(this.industry)
                    .minimumInvestmentValue(this.minimumInvestmentValue)
                    .teamLanguage(this.teamLanguage)
                    .projectBudget(this.projectBudget)
                    .expectedPaybackPeriod(this.expectedPaybackPeriod)
                    .investmentProposalUuid(investmentProposalUuid)
                    .userId(userId)
                    .build();
        }
    }


}

