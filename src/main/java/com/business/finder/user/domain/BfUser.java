package com.business.finder.user.domain;

import com.business.finder.investment.domain.InvestmentProposal;
import com.business.finder.jpa.BaseEntity;
import com.business.finder.partnership.domain.PartnershipProposal;
import com.business.finder.user.domain.type.BfUserStatus;
import com.business.finder.user.domain.type.BfUserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "BF_USER")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@ToString(exclude = {"password"})
public class BfUser extends BaseEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private BfUserType bfUserType;

    @Enumerated(EnumType.STRING)
    private BfUserStatus bfUserStatus; // TODO. Should be activated after paying.

    private String pictureUrl;

    private String description;

    @CollectionTable(
            name = "bf_user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    @OneToMany // TODO. Think about cascadeType in future. Not sure about archive process for partnership proposals.
    @JoinColumn(name = "bf_user_Id")
    private Set<PartnershipProposal> partnershipProposals = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "bf_user_Id")
    private Set<InvestmentProposal> investmentProposals = new HashSet<>();

    public BfUser(String email, String password, BfUserType bfUserType) {
        this.email = email;
        this.password = password;
        this.bfUserType = bfUserType;
        this.bfUserStatus = BfUserStatus.NEW;
        this.pictureUrl = null;
        this.description = null;
        this.roles = Set.of("ROLE_USER");
    }
}
