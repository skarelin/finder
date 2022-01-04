package com.business.finder.user.domain;

import com.business.finder.jpa.BaseEntity;
import com.business.finder.user.domain.type.ProfilePictureExtension;
import com.business.finder.user.domain.type.ProfilePictureStatus;
import com.business.finder.user.domain.type.ProfilePictureStorage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @Enumerated(EnumType.STRING)
    private ProfilePictureStatus status;

    @Enumerated(EnumType.STRING)
    private ProfilePictureExtension extension;

    @NotNull
    private Long userId;

    public BfProfilePicture(String fileName, Long userId, ProfilePictureStorage storage, ProfilePictureExtension extension) {
        this.fileName = fileName;
        this.userId = userId;
        this.pictureStorage = storage;
        this.extension = extension;
        this.status = ProfilePictureStatus.NEW;
    }

    public BfProfilePicture(Long userId) {
        this.userId = userId;
    }
}
