package com.business.finder.user.domain;

import com.business.finder.jpa.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class BfUser extends BaseEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String email;
    private String password;

//    @Enumerated(EnumType.STRING)
//    private BfUserType bfUserType; // TODO. Should be activated after paying.

    @CollectionTable(
            name = "bf_user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    public BfUser(String email, String password) {
        this.email = email;
        this.password = password;
        this.roles = Set.of("ROLE_USER");
    }
}
