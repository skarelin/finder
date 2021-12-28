package com.business.finder.partnership.web;

import com.business.finder.metadata.Country;
import com.business.finder.metadata.Industry;
import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase;
import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase.CreatePartnershipProposalCommand;
import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase.PartnershipProposalResponse;
import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase.UpdatePartnershipProposalCommand;
import com.business.finder.security.UserEntityDetails;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/partnership-proposal")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@RequiredArgsConstructor
public class PartnershipProposalController {

    private final QueryPartnershipProposalUseCase queryPartnershipProposalUseCase;

    @PostMapping
    public ResponseEntity<PartnershipProposalResponse> createPartnershipProposal(@Valid @RequestBody RestCreatePartnershipProposal command,
                                                                                 @AuthenticationPrincipal UserEntityDetails userEntityDetails) {
        PartnershipProposalResponse result = queryPartnershipProposalUseCase.create(command.toCreatePartnershipProposalCommand(userEntityDetails.getCurrentUserId()));

        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @GetMapping("/{uuid}")
    public void getPartnershipProposalByUuid(@PathVariable String uuid) {

    }

    @GetMapping("/all")
    public void getAllPartnershipProposal() {
        // should be pageable
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PartnershipProposalResponse> updatePartnershipProposalByUuid(@PathVariable String uuid,
                                                                                       @Valid @RequestBody RestUpdatePartnershipProposal command) {
        PartnershipProposalResponse result = queryPartnershipProposalUseCase.update(command.toUpdatePartnershipProposal());

        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @DeleteMapping("/uuid")
    public void deletePartnershipProposalByUuid(@PathVariable String uuid) {

    }


    //TODO. Set also MAX value here for fields.
    @Data
    static class RestCreatePartnershipProposal {
        @NotBlank
        @Size(min = 5)
        String subject;
        @NotNull Industry industry;
        @NotNull Country country;
        @NotBlank String knowledgeOfProposalCreator;
        @NotNull Boolean isTeamAvailable;
        String teamDescription;
        String additionalDescription;
        Long userId;

        public CreatePartnershipProposalCommand toCreatePartnershipProposalCommand(Long userId) {
            return CreatePartnershipProposalCommand.builder()
                    .subject(this.subject)
                    .industry(this.industry)
                    .country(this.country)
                    .knowledgeOfProposalCreator(this.knowledgeOfProposalCreator)
                    .teamAvailable(this.isTeamAvailable)
                    .teamDescription(this.teamDescription)
                    .additionalDescription(this.additionalDescription)
                    .userId(userId)
                    .build();
        }
    }

    //TODO. Set also MAX value here for fields.
    @Data
    static class RestUpdatePartnershipProposal {
        @NotBlank
        String partnershipProposalUuid;
        @NotBlank
        @Size(min = 5)
        String subject;
        @NotNull Industry industry;
        @NotNull Country country;
        @NotBlank String knowledgeOfProposalCreator;
        @NotNull Boolean isTeamAvailable;
        String teamDescription;
        String additionalDescription;

        public UpdatePartnershipProposalCommand toUpdatePartnershipProposal() {
            return UpdatePartnershipProposalCommand.builder()
                    .partnershipProposalUuid(this.partnershipProposalUuid)
                    .subject(this.subject)
                    .industry(this.industry)
                    .country(this.country)
                    .knowledgeOfProposalCreator(this.knowledgeOfProposalCreator)
                    .teamAvailable(this.isTeamAvailable)
                    .teamDescription(this.teamDescription)
                    .additionalDescription(this.additionalDescription)
                    .build();
        }
    }
}
