package com.business.finder.user.application.validator;

import com.business.finder.user.application.port.UploadUserProfilePictureUseCase.ErrorCode;
import com.business.finder.user.domain.type.ProfilePictureExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UploadUserProfilePictureValidator {

    @Value("${bf.user.profile.picture.allowed-extensions}")
    private List<String> allowedProfilePictureExtensions;

    public List<ErrorCode> validate(ProfilePictureExtension pictureExtension) {
        List<ErrorCode> errors = new ArrayList<>();
        boolean isAllowed = allowedProfilePictureExtensions
                .stream()
                .map(allowedExtension -> ProfilePictureExtension.valueOf(allowedExtension.toUpperCase()))
                .anyMatch(pictureExtension::equals);
        if (!isAllowed) {
            errors.add(ErrorCode.NOT_ALLOWED_EXTENSION);
        }
        return errors;
    }
}
