package com.business.finder.partnership.application;

import com.business.finder.partnership.domain.PartnershipProposal;
import org.springframework.stereotype.Component;

@Component
class PartnershipProposalMapper {
    PartnershipProposalResponse toPartnershipProposalResponse(PartnershipProposal proposal) {
        return PartnershipProposalResponse.builder()
                .uuid(proposal.getUuid())
                .subject(proposal.getSubject())
                .industry(proposal.getIndustry())
                .country(proposal.getCountry())
                .knowledgeOfProposalCreator(proposal.getKnowledgeOfProposalCreator())
                .teamAvailable(proposal.isTeamAvailable())
                .teamDescription(proposal.getTeamDescription())
                .additionalDescription(proposal.getAdditionalDescription())
                .build();
    }
}
