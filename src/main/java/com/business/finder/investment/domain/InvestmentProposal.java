package com.business.finder.investment.domain;

import com.business.finder.jpa.BaseEntity;
import com.business.finder.metadata.Country;
import com.business.finder.metadata.Industry;
import com.business.finder.metadata.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BF_INVESTMENT_PROPOSAL")
public class InvestmentProposal extends BaseEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastUpdateAt;

    private String projectSubject;

    private String projectDescription;

    @Enumerated(EnumType.STRING)
    private Country country;

    private String city;

    @Enumerated(EnumType.STRING)
    private Industry industry;

    private int minimumInvestmentValue;

    @Enumerated(EnumType.STRING)
    private Language teamLanguage;

    private int projectBudget;

    private int expectedPaybackPeriod;

    @Column(name = "bf_user_Id")
    private Long userId;


    public InvestmentProposal(String projectSubject,
                              String projectDescription,
                              Country country, String city,
                              Industry industry,
                              int minimumInvestmentValue,
                              Language teamLanguage,
                              int projectBudget,
                              int expectedPaybackPeriod,
                              Long userId) {
        this.projectSubject = projectSubject;
        this.projectDescription = projectDescription;
        this.country = country;
        this.city = city;
        this.industry = industry;
        this.minimumInvestmentValue = minimumInvestmentValue;
        this.teamLanguage = teamLanguage;
        this.projectBudget = projectBudget;
        this.expectedPaybackPeriod = expectedPaybackPeriod;
        this.userId = userId;
    }
}
