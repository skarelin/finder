package com.business.finder.partnership.web;

import com.business.finder.metadata.Country;
import com.business.finder.metadata.Industry;
import com.business.finder.partnership.application.port.CreatePartnershipProposalUseCase;
import com.business.finder.partnership.application.port.CreatePartnershipProposalUseCase.CreatePartnershipProposalCommand;
import com.business.finder.partnership.application.port.CreatePartnershipProposalUseCase.CreatePartnershipProposalResponse;
import com.business.finder.security.UserSecurity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final CreatePartnershipProposalUseCase createPartnershipProposalUseCase;
    private final UserSecurity userSecurity;

    @PostMapping
    public ResponseEntity<CreatePartnershipProposalResponse> createPartnershipProposal(@Valid @RequestBody RestCreatePartnershipProposal command) {
        CreatePartnershipProposalResponse result = createPartnershipProposalUseCase.create(command.toCreatePartnershipProposalCommand());

        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    // TODO. Temporary method. Remove it later.
    // Just example how to use the @AuthentificationPrincipal annotation.
    // We can put User object into command object. Later in service we will use our UserSecurity wrapper for checking if it's admin or owner.
    @GetMapping
    public Object shouldBeAuthorized(@AuthenticationPrincipal UserDetails object) {
        return object.getUsername();
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

        public CreatePartnershipProposalCommand toCreatePartnershipProposalCommand() {
            return CreatePartnershipProposalCommand.builder()
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
