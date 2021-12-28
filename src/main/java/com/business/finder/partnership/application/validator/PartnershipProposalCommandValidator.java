package com.business.finder.partnership.application.validator;

import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase.CreatePartnershipProposalCommand;
import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase.Error;
import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase.UpdatePartnershipProposalCommand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase.Error.*;

@Component
public class PartnershipProposalCommandValidator {

    public List<Error> validateCreateCommand(CreatePartnershipProposalCommand command) {
        List<Error> errors = new ArrayList<>();

        if (command.isTeamAvailable() && StringUtils.isBlank(command.getTeamDescription())) {
            errors.add(TEAM_DESCRIPTION_SHOULD_BE_PRESENT);
        }

        if (!command.isTeamAvailable() && StringUtils.isNotBlank(command.getTeamDescription())) {
            errors.add(TEAM_DESCRIPTION_SHOULD_NOT_BE_PRESENT);
        }

        if(command.getUserId() == null) {
            errors.add(USER_IS_NOT_PRESENT);
        }

        return errors;
    }

    public List<Error> validateUpdateCommand(UpdatePartnershipProposalCommand command) {
        List<Error> errors = new ArrayList<>();

        if (command.isTeamAvailable() && StringUtils.isBlank(command.getTeamDescription())) {
            errors.add(TEAM_DESCRIPTION_SHOULD_BE_PRESENT);
        }

        if (!command.isTeamAvailable() && StringUtils.isNotBlank(command.getTeamDescription())) {
            errors.add(TEAM_DESCRIPTION_SHOULD_NOT_BE_PRESENT);
        }
        return errors;
    }
}
