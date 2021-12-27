package com.business.finder.user.web;

import com.business.finder.user.application.port.DeleteUserUseCase;
import com.business.finder.user.application.port.DeleteUserUseCase.DeleteUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.business.finder.user.application.port.DeleteUserUseCase.Error.PROVIDED_EMAIL_IS_NOT_ASSIGNED_TO_USER;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class UserController {

    private final DeleteUserUseCase deleteUserUseCase;

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
}
