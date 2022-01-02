package com.business.finder.user.domain;

import com.business.finder.jpa.BaseEntity;
import com.business.finder.user.domain.type.ProfilePictureStorage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "BF_PROFILE_PICTURE")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
// Why just not field in BfUser entity? At this moment we are not sure where will be stored pictures of users.
// It can be cloud, external-service, local machine, some other solution.
// That's why at this moment we need some extra information. Maybe, this entity will be useful in the future.
public class BfProfilePicture extends BaseEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    private String fileName;

    @Enumerated(EnumType.STRING)
    private ProfilePictureStorage pictureStorage;

    private Long userId;

    public BfProfilePicture(String fileName, Long userId) {
        this.fileName = fileName;
        this.userId = userId;
        this.pictureStorage = ProfilePictureStorage.LOCAL;
    }
}
