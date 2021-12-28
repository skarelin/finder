package com.business.finder.user.web;

import com.business.finder.user.application.port.DeleteUserUseCase;
import com.business.finder.user.application.port.DeleteUserUseCase.DeleteUserResponse;
import com.business.finder.user.application.port.UpdateUserUseCase;
import com.business.finder.user.application.port.UpdateUserUseCase.UpdateUserCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import static com.business.finder.user.application.port.DeleteUserUseCase.Error.PROVIDED_EMAIL_IS_NOT_ASSIGNED_TO_USER;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class UserController {

    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @DeleteMapping
    public ResponseEntity<DeleteUserResponse> deleteUser(@RequestParam String email, @AuthenticationPrincipal UserDetails user) {
        if (!email.equalsIgnoreCase(user.getUsername())) {
            ResponseEntity.badRequest()
                    .body(DeleteUserResponse.error(PROVIDED_EMAIL_IS_NOT_ASSIGNED_TO_USER));
        }
        DeleteUserResponse response = deleteUserUseCase.deleteUser(email, user);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    // TODO. Profile picture should be here. Probably MultipartFile and send it to server;
    @PutMapping
    public void updateUser(@Valid @RequestBody RestUpdateUserCommand command, @AuthenticationPrincipal UserDetails user) {
        updateUserUseCase.update(command.toUpdateUserCommand(user.getUsername()));
    }

    @Data
    static class RestUpdateUserCommand {
        @Size(max = 3000)
        String profileDescription;

        UpdateUserCommand toUpdateUserCommand(String email) {
            return new UpdateUserCommand(this.profileDescription, email);
        }
    }
}
