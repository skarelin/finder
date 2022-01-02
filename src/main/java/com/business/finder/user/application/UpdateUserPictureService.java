package com.business.finder.user.application;

import com.business.finder.user.application.port.UpdateUserPictureUserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
class UpdateUserPictureService implements UpdateUserPictureUserCase {

    // TODO. Add white list of allowed formats.

    @Value("${bf.user.profile.picture.folder}")
    private String uploadPath;

    @Override
    public UpdateUserPictureUserResponse update(UpdateUserPictureCommand command) {
        @NotNull MultipartFile file = command.getMultipartFile();
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                initializeDirectories();
            }
            Files.copy(file.getInputStream(), root.resolve("user-id" + "." + StringUtils.getFilenameExtension(file.getOriginalFilename())));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e);
        }

        return UpdateUserPictureUserResponse.OK;
    }

    @PostConstruct
    public void initializeDirectories() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder! Error: " + e);
        }
    }
}
