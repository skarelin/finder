package com.business.finder.user.application.port;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;

public interface UpdateUserUseCase {
    UpdateUserResponse update(UpdateUserCommand command);

    @Value
    class UpdateUserResponse {
        public static UpdateUserResponse OK = new UpdateUserResponse(true, Collections.emptyList());

        boolean success;
        List<DeleteUserUseCase.Error> errors;// TODO(skarelin): DeleteUserUseCase.Error?? Fix it.
    }

    @Value
    class UpdateUserCommand {
        String userDescription;
        @NotBlank String userEmail;
    }
}
