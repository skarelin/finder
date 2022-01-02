package com.business.finder.user.application.port;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

public interface UploadUserProfilePictureUseCase {

    Response upload(UploadUserProfilePictureCommand command);

    @Value
    class Response {

        public static Response OK = new Response(true, Collections.emptyList());

        public static Response error(Error error) {
            return new Response(false, Collections.singletonList(error));
        }

        boolean success;
        List<Error> errors;
    }

    @Value
    class UploadUserProfilePictureCommand {
        @NotNull MultipartFile file;
        @NotNull Long userId;
    }

    enum Error {

    }
}
