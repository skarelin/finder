package com.business.finder.partnership.domain;

import com.business.finder.jpa.BaseEntity;
import com.business.finder.metadata.Country;
import com.business.finder.metadata.Industry;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "BF_PARTNERSHIP_PROPOSAL")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class PartnershipProposal extends BaseEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    private String subject;

    @Enumerated(EnumType.STRING)
    private Industry industry;

    @Enumerated(EnumType.STRING)
    private Country country;

    private String knowledgeOfProposalCreator;

    private boolean teamAvailable;

    private String teamDescription;

    private String additionalDescription;

//    @ManyToOne
//    private User user;


    public PartnershipProposal(String subject,
                               Industry industry,
                               Country country,
                               String knowledgeOfProposalCreator,
                               boolean teamAvailable,
                               String teamDescription,
                               String additionalDescription) {
        this.subject = subject;
        this.industry = industry;
        this.country = country;
        this.knowledgeOfProposalCreator = knowledgeOfProposalCreator;
        this.teamAvailable = teamAvailable;
        this.teamDescription = teamDescription;
        this.additionalDescription = additionalDescription;
    }
}
