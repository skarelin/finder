package com.business.finder.partnership.application.port;

import com.business.finder.metadata.Country;
import com.business.finder.metadata.Industry;
import com.business.finder.partnership.domain.PartnershipProposal;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public interface QueryPartnershipProposalUseCase {

    PartnershipProposalResponse create(CreatePartnershipProposalCommand command);

    PartnershipProposalResponse update(UpdatePartnershipProposalCommand command);

    @Value
    @Builder
    class CreatePartnershipProposalCommand {
        String partnershipProposalUuid;
        String subject;
        Industry industry;
        Country country;
        String knowledgeOfProposalCreator;
        boolean teamAvailable;
        String teamDescription;
        String additionalDescription;
        Long userId;

        public PartnershipProposal toPartnershipProposal() {
            return new PartnershipProposal(this.subject,
                    this.industry,
                    this.country,
                    this.knowledgeOfProposalCreator,
                    this.teamAvailable,
                    this.teamDescription,
                    this.additionalDescription,
                    this.userId);
        }
    }

    @Value
    @Builder
    class UpdatePartnershipProposalCommand {
        String partnershipProposalUuid;
        String subject;
        Industry industry;
        Country country;
        String knowledgeOfProposalCreator;
        boolean teamAvailable;
        String teamDescription;
        String additionalDescription;
    }

    @Value
    class PartnershipProposalResponse {
        public static PartnershipProposalResponse OK = new PartnershipProposalResponse(true, Collections.emptyList());

        public static PartnershipProposalResponse errors(Error... errors) {
            return new PartnershipProposalResponse(true, Arrays.asList(errors));
        }

        public static PartnershipProposalResponse errors(List<Error> errors) {
            return new PartnershipProposalResponse(true, errors);
        }

        boolean success;
        List<Error> errors;
    }

    @Getter
    enum Error {
        TEAM_DESCRIPTION_SHOULD_BE_PRESENT,
        TEAM_DESCRIPTION_SHOULD_NOT_BE_PRESENT,
        USER_IS_NOT_PRESENT;

//        private final HttpStatus status; // In some cases it also can be here. (for example when we are using structure with Either object);
    }
}
