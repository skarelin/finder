package com.business.finder.partnership.web;

import com.business.finder.metadata.Country;
import com.business.finder.metadata.Industry;
import com.business.finder.partnership.application.PartnershipProposalResponse;
import com.business.finder.partnership.application.port.PageableFetchingPartnershipProposalUseCase;
import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase;
import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase.CreatePartnershipProposalCommand;
import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase.RemovePartnershipProposalCommand;
import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase.Response;
import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase.UpdatePartnershipProposalCommand;
import com.business.finder.security.UserEntityDetails;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@RestController
@RequestMapping("/partnership-proposal")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@RequiredArgsConstructor
public class PartnershipProposalController {

    private final QueryPartnershipProposalUseCase queryPartnershipProposalUseCase;
    private final PageableFetchingPartnershipProposalUseCase pageableFetchingPartnershipProposalUseCase;

    @PostMapping
    public ResponseEntity<Response> createPartnershipProposal(@Valid @RequestBody RestCreatePartnershipProposal command,
                                                              @AuthenticationPrincipal UserEntityDetails userEntityDetails) {
        Response result = queryPartnershipProposalUseCase.create(command.toCreatePartnershipProposalCommand(userEntityDetails.getCurrentUserId()));

        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PartnershipProposalResponse> getPartnershipProposalByUuid(@PathVariable String uuid) {
        Optional<PartnershipProposalResponse> partnershipProposal = queryPartnershipProposalUseCase.findByUuid(uuid);
        return partnershipProposal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public Page<PartnershipProposalResponse> getAllPartnershipProposal(Pageable pageable) {
        return pageableFetchingPartnershipProposalUseCase.fetch(pageable);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Response> updatePartnershipProposalByUuid(@PathVariable String uuid,
                                                                    @Valid @RequestBody RestUpdatePartnershipProposal command,
                                                                    @AuthenticationPrincipal UserEntityDetails userEntityDetails) {
        Response result = queryPartnershipProposalUseCase.update(command.toUpdatePartnershipProposal(uuid, userEntityDetails.getCurrentUserId()));

        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @DeleteMapping("/uuid")
    public ResponseEntity<Response> deletePartnershipProposalByUuid(@PathVariable String uuid,
                                                                    @AuthenticationPrincipal UserEntityDetails userEntityDetails) {
        return ResponseEntity.ok(
                queryPartnershipProposalUseCase.remove(new RemovePartnershipProposalCommand(uuid, userEntityDetails.getCurrentUserId())));
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
        @Size(min = 5)
        String subject;
        @NotNull Industry industry;
        @NotNull Country country;
        @NotBlank String knowledgeOfProposalCreator;
        @NotNull Boolean isTeamAvailable;
        String teamDescription;
        String additionalDescription;

        public UpdatePartnershipProposalCommand toUpdatePartnershipProposal(String partnershipProposalUuid, Long userId) {
            return UpdatePartnershipProposalCommand.builder()
                    .partnershipProposalUuid(partnershipProposalUuid)
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
}
