package com.business.finder.partnership.application;

import com.business.finder.metadata.Country;
import com.business.finder.metadata.Industry;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PartnershipProposalResponse {
    String uuid;
    String subject;
    Industry industry;
    Country country;
    String knowledgeOfProposalCreator;
    boolean teamAvailable;
    String teamDescription;
    String additionalDescription;
}
