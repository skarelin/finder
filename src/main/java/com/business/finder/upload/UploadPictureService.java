package com.business.finder.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class UploadPictureService {

    @Value("${bf.user.profile.picture.folder}")
    private String uploadPath;

    public void uploadProfilePicture() {

    }

}
