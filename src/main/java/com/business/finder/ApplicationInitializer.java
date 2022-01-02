package com.business.finder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class ApplicationInitializer implements ApplicationRunner {

    @Value("${bf.user.profile.picture.folder}")
    private String profilePictureUploadPath;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createDirectoriesForLocalProfilePictures();
    }

    private void createDirectoriesForLocalProfilePictures() {
        try {
            Files.createDirectories(Paths.get(profilePictureUploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder! Error: " + e);
        }
    }
}
