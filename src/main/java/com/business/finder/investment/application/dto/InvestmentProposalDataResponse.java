package com.business.finder.investment.application.dto;

import com.business.finder.metadata.Country;
import com.business.finder.metadata.Industry;
import com.business.finder.metadata.Language;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
public class InvestmentProposalDataResponse {
    private String projectSubject;
    private String projectDescription;
    private Country country;
    private String city;
    private Industry industry;
    private int minimumInvestmentValue;
    private Language teamLanguage;
    private int projectBudget;
    private int expectedPaybackPeriod;
    private String investmentProposalUuid;
    private boolean isOwner;
}
