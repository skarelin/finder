package com.business.finder.partnership.application;

import com.business.finder.partnership.application.exception.PartnershipProposalIsNotFoundException;
import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase;
import com.business.finder.partnership.db.PartnershipProposalRepository;
import com.business.finder.partnership.domain.PartnershipProposal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
class QueryPartnershipProposalService implements QueryPartnershipProposalUseCase {

    private final PartnershipProposalRepository repository;
    private final PartnershipProposalCommandValidator commandValidator;
    private final PartnershipProposalMapper mapper;

    @Override
    public Response create(CreatePartnershipProposalCommand command) {
        List<Error> errors = commandValidator.validateCreateCommand(command);

        if (errors.isEmpty()) {
            PartnershipProposal partnershipProposal = command.toPartnershipProposal();
            repository.save(partnershipProposal);
            return Response.OK;
        } else {
            return Response.errors(errors);
        }
    }


    @Override
    @Transactional
    public Response update(UpdatePartnershipProposalCommand command) {
        List<Error> errors = commandValidator.validateUpdateCommand(command);

        if (errors.isEmpty()) {
            return repository.findByUuid(command.getPartnershipProposalUuid())
                    .map(partnershipProposal -> {
                        updateFields(command, partnershipProposal);
                        return Response.OK;
                    })
                    .orElseThrow(() -> new PartnershipProposalIsNotFoundException("Partnership proposal is not found during updating request. UUID: " + command.getPartnershipProposalUuid()));
        } else {
            return Response.errors(errors);
        }
    }

    @Override
    public Optional<PartnershipProposalResponse> findByUuid(String uuid) {
        Optional<PartnershipProposal> partnershipProposal = repository.findByUuid(uuid);
        return partnershipProposal.map(mapper::toPartnershipProposalResponse);
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
