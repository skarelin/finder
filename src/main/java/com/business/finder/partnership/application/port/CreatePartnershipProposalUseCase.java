package com.business.finder.partnership.application.port;

import com.business.finder.metadata.Country;
import com.business.finder.metadata.Industry;
import com.business.finder.partnership.domain.PartnershipProposal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface CreatePartnershipProposalUseCase {

    CreatePartnershipProposalResponse create(CreatePartnershipProposalCommand command);

    @Value
    @Builder
    class CreatePartnershipProposalCommand {
        String subject;
        Industry industry;
        Country country;
        String knowledgeOfProposalCreator;
        boolean teamAvailable;
        String teamDescription;
        String additionalDescription;

        public PartnershipProposal toPartnershipProposal() {
            return new PartnershipProposal(this.subject,
                    this.industry,
                    this.country,
                    this.knowledgeOfProposalCreator,
                    this.teamAvailable,
                    this.teamDescription,
                    this.additionalDescription);
        }
    }

    @Value
    class CreatePartnershipProposalResponse {
        public static CreatePartnershipProposalResponse OK = new CreatePartnershipProposalResponse(true, Collections.emptyList());

        public static CreatePartnershipProposalResponse errors(Error... errors) {
            return new CreatePartnershipProposalResponse(true, Arrays.asList(errors));
        }

        public static CreatePartnershipProposalResponse errors(List<Error> errors) {
            return new CreatePartnershipProposalResponse(true, errors);
        }

        boolean success;
        List<Error> errors;
    }

    @AllArgsConstructor
    @Getter
    enum Error {
        TEAM_DESCRIPTION_SHOULD_BE_PRESENT,
        TEAM_DESCRIPTION_SHOULD_NOT_BE_PRESENT;

//        private final HttpStatus status; // In some cases it also can be here. (for example when we are using structure with Either object);
    }
}
