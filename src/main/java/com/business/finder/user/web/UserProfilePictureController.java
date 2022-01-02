package com.business.finder.user.web;

import com.business.finder.security.UserEntityDetails;
import com.business.finder.user.application.port.UpdateUserPictureUserCase;
import com.business.finder.user.application.port.UpdateUserPictureUserCase.UpdateUserPictureCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user/profile-picture/")
@RequiredArgsConstructor
public class UserProfilePictureController {

    private final UpdateUserPictureUserCase updateUserPictureUserCase;

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void uploadUserPicture(@RequestParam MultipartFile file,
                                  @AuthenticationPrincipal UserEntityDetails userEntityDetails) {
        updateUserPictureUserCase.update(new UpdateUserPictureCommand(userEntityDetails.getCurrentUserId(), file));
    }

}
