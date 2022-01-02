package com.business.finder.user.application.port;

import com.business.finder.user.domain.type.BfUserType;
import lombok.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface CreateUserUseCase {

    CreateUserResponse createUser(CreateUserCommand command);

    @Value
    class CreateUserCommand {
        String email;
        String password;
        BfUserType userType;
    }

    @Value
    class CreateUserResponse {
        public static CreateUserResponse OK = new CreateUserResponse(true, Collections.emptyList());

        public static CreateUserResponse error(Error userErrorCode) {
            return new CreateUserResponse(false, Collections.singletonList(userErrorCode));
        }

        public static CreateUserResponse errors(Error... errors) {
            return new CreateUserResponse(false, Arrays.asList(errors));
        }

        boolean success;
        List<Error> errors;
    }

    enum Error {
        USER_ALREADY_EXISTS
    }
}
