package com.business.finder.user.application.port;

import lombok.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;

public interface DeleteUserUseCase {

    DeleteUserResponse deleteUser(String userEmail, UserDetails command);

    @Value
    class DeleteUserResponse {
        public static DeleteUserResponse OK = new DeleteUserResponse(true, Collections.emptyList());

        public static DeleteUserResponse error(Error error) {
            return new DeleteUserResponse(false, Collections.singletonList(error));
        }

        boolean success;
        List<Error> errors;
    }

    enum Error {
        PROVIDED_EMAIL_IS_NOT_ASSIGNED_TO_USER
    }
}
