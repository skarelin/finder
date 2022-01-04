package com.business.finder.investment.application.dto;

import com.business.finder.metadata.Country;
import com.business.finder.metadata.Industry;
import com.business.finder.metadata.Language;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InvestmentProposalDataResponse {
    String projectSubject;
    String projectDescription;
    Country country;
    String city;
    Industry industry;
    int minimumInvestmentValue;
    Language teamLanguage;
    int projectBudget;
    int expectedPaybackPeriod;
    String investmentProposalUuid;
    boolean isOwner;
}
