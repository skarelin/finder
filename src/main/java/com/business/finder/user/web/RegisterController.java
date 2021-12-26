package com.business.finder.user.web;

import com.business.finder.user.application.port.CreateUserUseCase;
import com.business.finder.user.application.port.CreateUserUseCase.CreateUserCommand;
import com.business.finder.user.application.port.CreateUserUseCase.CreateUserResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final CreateUserUseCase createUserUseCase;

    @PostMapping
    public ResponseEntity<CreateUserResponse> registerUser(@Valid @RequestBody RestRegisterUserCommand command) {
        CreateUserResponse result = createUserUseCase.createUser(command.toCreateUserCommand());
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(result);
        }
    }

    @Data
    static class RestRegisterUserCommand {
        @Email(regexp = ".+@.+\\..+")
        @NotNull
        String email;

        @Size(min = 3, max = 100)
        @NotNull
        String password;

        CreateUserCommand toCreateUserCommand() {
            return new CreateUserCommand(this.email, this.password);
        }
    }
}
