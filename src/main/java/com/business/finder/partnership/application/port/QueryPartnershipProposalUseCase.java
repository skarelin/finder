package com.business.finder.partnership.application.port;

import com.business.finder.metadata.Country;
import com.business.finder.metadata.Industry;
import com.business.finder.partnership.application.PartnershipProposalResponse;
import com.business.finder.partnership.domain.PartnershipProposal;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface QueryPartnershipProposalUseCase {

    Response create(CreatePartnershipProposalCommand command);

    Response update(UpdatePartnershipProposalCommand command);

    Optional<PartnershipProposalResponse> findByUuid(String uuid);

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
    class Response {
        public static Response OK = new Response(true, Collections.emptyList());

        public static Response errors(Error... errors) {
            return new Response(true, Arrays.asList(errors));
        }

        public static Response errors(List<Error> errors) {
            return new Response(true, errors);
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
