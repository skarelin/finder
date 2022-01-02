package com.business.finder.user.application.port;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

public interface UpdateUserPictureUserCase {

    UpdateUserPictureUserResponse update(UpdateUserPictureCommand command);

    @Value
    class UpdateUserPictureUserResponse {
        public static UpdateUserPictureUserResponse OK = new UpdateUserPictureUserResponse(true, Collections.emptyList());

        boolean success;
        List<Error> errors;
    }

    enum Error {

    }

    @Value
    class UpdateUserPictureCommand {
        @NotNull Long userId;
        @NotNull MultipartFile multipartFile;
    }
}
