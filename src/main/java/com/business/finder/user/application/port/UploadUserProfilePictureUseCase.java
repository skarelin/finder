package com.business.finder.user.application.port;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

public interface UploadUserProfilePictureUseCase {

    UploadUserProfilePictureResponse upload(UploadUserProfilePictureCommand command);

    @Value
    class UploadUserProfilePictureResponse {

        public static UploadUserProfilePictureResponse OK = new UploadUserProfilePictureResponse(true, Collections.emptyList());

        public static UploadUserProfilePictureResponse errors(List<ErrorCode> errors) {
            return new UploadUserProfilePictureResponse(false, errors);
        }

        boolean success;
        List<ErrorCode> errors;
    }

    @Value
    class UploadUserProfilePictureCommand {
        @NotNull MultipartFile file;
        @NotNull Long userId;
    }

    enum ErrorCode {
        NOT_ALLOWED_EXTENSION
    }
}
