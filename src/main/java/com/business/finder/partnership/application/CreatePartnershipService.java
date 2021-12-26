package com.business.finder.partnership.application;

import com.business.finder.partnership.application.port.CreatePartnershipProposalUseCase;
import com.business.finder.partnership.db.PartnershipProposalRepository;
import com.business.finder.partnership.domain.PartnershipProposal;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.business.finder.partnership.application.port.CreatePartnershipProposalUseCase.Error.TEAM_DESCRIPTION_SHOULD_BE_PRESENT;
import static com.business.finder.partnership.application.port.CreatePartnershipProposalUseCase.Error.TEAM_DESCRIPTION_SHOULD_NOT_BE_PRESENT;


@Service
@RequiredArgsConstructor
class CreatePartnershipService implements CreatePartnershipProposalUseCase {

    private final PartnershipProposalRepository repository;

    @Override
    public CreatePartnershipProposalResponse create(CreatePartnershipProposalCommand command) {
        List<Error> errors = new ArrayList<>();

        if (command.isTeamAvailable() && StringUtils.isBlank(command.getTeamDescription())) {
            errors.add(TEAM_DESCRIPTION_SHOULD_BE_PRESENT);
        }

        if (!command.isTeamAvailable() && StringUtils.isNotBlank(command.getTeamDescription())) {
            errors.add(TEAM_DESCRIPTION_SHOULD_NOT_BE_PRESENT);
        }
        // At this moment don't see any additional checks for PartnershipProposal.
        // Refactor may be needed later.

        if (errors.isEmpty()) {
            PartnershipProposal partnershipProposal = command.toPartnershipProposal();
            repository.save(partnershipProposal);
            return CreatePartnershipProposalResponse.OK;
        } else {
            return CreatePartnershipProposalResponse.errors(errors);
        }
    }

}
