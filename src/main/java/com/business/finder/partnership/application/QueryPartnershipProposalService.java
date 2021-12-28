package com.business.finder.partnership.application;

import com.business.finder.partnership.application.exception.PartnershipProposalIsNotFoundException;
import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase;
import com.business.finder.partnership.application.validator.PartnershipProposalCommandValidator;
import com.business.finder.partnership.db.PartnershipProposalRepository;
import com.business.finder.partnership.domain.PartnershipProposal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
class QueryPartnershipProposalService implements QueryPartnershipProposalUseCase {

    private final PartnershipProposalRepository repository;
    private final PartnershipProposalCommandValidator commandValidator;

    @Override
    public PartnershipProposalResponse create(CreatePartnershipProposalCommand command) {
        List<Error> errors = commandValidator.validateCreateCommand(command);

        if (errors.isEmpty()) {
            PartnershipProposal partnershipProposal = command.toPartnershipProposal();
            repository.save(partnershipProposal);
            return PartnershipProposalResponse.OK;
        } else {
            return PartnershipProposalResponse.errors(errors);
        }
    }


    @Override
    @Transactional
    public PartnershipProposalResponse update(UpdatePartnershipProposalCommand command) {
        List<Error> errors = commandValidator.validateUpdateCommand(command);

        if (errors.isEmpty()) {
            return repository.findByUuid(command.getPartnershipProposalUuid())
                    .map(partnershipProposal -> {
                        updateFields(command, partnershipProposal);
                        return PartnershipProposalResponse.OK;
                    })
                    .orElseThrow(() -> new PartnershipProposalIsNotFoundException("Partnership proposal is not found during updating request. UUID: " + command.getPartnershipProposalUuid()));
        } else {
            return PartnershipProposalResponse.errors(errors);
        }
    }

    private PartnershipProposal updateFields(UpdatePartnershipProposalCommand command, PartnershipProposal partnershipProposal) {
        partnershipProposal.setSubject(command.getSubject());
        partnershipProposal.setIndustry(command.getIndustry());
        partnershipProposal.setCountry(command.getCountry());
        partnershipProposal.setKnowledgeOfProposalCreator(command.getKnowledgeOfProposalCreator());
        partnershipProposal.setTeamAvailable(command.isTeamAvailable());
        partnershipProposal.setTeamDescription(command.getTeamDescription());
        partnershipProposal.setAdditionalDescription(command.getAdditionalDescription());
        return partnershipProposal;
    }

}
