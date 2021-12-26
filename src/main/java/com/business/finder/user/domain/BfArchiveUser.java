package com.business.finder.user.domain;

import com.business.finder.jpa.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "BF_ARCHIVE_USER")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class BfArchiveUser extends BaseEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    private String email;

    public BfArchiveUser(String email) {
        this.email = email;
    }
}
