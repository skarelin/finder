package com.business.finder.partnership.application;

import com.business.finder.partnership.application.exception.NoAccessToPartnershipProposalException;
import com.business.finder.partnership.application.exception.PartnershipProposalIsNotFoundException;
import com.business.finder.partnership.application.port.QueryPartnershipProposalUseCase;
import com.business.finder.partnership.db.PartnershipProposalRepository;
import com.business.finder.partnership.domain.PartnershipProposal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
class QueryPartnershipProposalService implements QueryPartnershipProposalUseCase {

    private final PartnershipProposalRepository repository;
    private final PartnershipProposalCommandValidator commandValidator;
    private final PartnershipProposalMapper mapper;

    @Override
    public Response create(CreatePartnershipProposalCommand command) {
        List<Error> errors = commandValidator.validateCreateCommand(command);

        if (errors.isEmpty()) {
            PartnershipProposal partnershipProposal = command.toPartnershipProposal();
            PartnershipProposal savedProposal = repository.save(partnershipProposal);
            log.info("Created partnership proposal " + savedProposal.getUuid() + " by user " + command.getUserId());
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
                    .map(partnershipProposal -> authorize(partnershipProposal, command.getUserId()))
                    .map(partnershipProposal -> {
                        updateFields(command, partnershipProposal);
                        log.info("Updated partnership proposal " + partnershipProposal.getUuid() + " by user " + command.getUserId());
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

    @Override
    public Response remove(RemovePartnershipProposalCommand command) {
        PartnershipProposal partnershipProposal = repository.findByUuid(command.getPartnershipProposalUuid())
                .map(proposal -> authorize(proposal, command.getCurrentUserId()))
                .orElseThrow(() -> new PartnershipProposalIsNotFoundException("Given partnership proposal not found. UUID: " + command.getPartnershipProposalUuid()));
        log.info("Removing partnership proposal " + partnershipProposal.getUuid() + " by user " + command.getCurrentUserId());
        repository.delete(partnershipProposal);
        return Response.OK;
    }

    private PartnershipProposal authorize(PartnershipProposal partnershipProposal, Long userId) {
        if (!partnershipProposal.getBfUserId().equals(userId)) {
            log.error("User " + userId + " tried to get access for not his proposal " + partnershipProposal.getUuid());
            throw new NoAccessToPartnershipProposalException("Current user doesn't have permission to partnership proposal " + partnershipProposal.getUuid());
        }
        return partnershipProposal;
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
