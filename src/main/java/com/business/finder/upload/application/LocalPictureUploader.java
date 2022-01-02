package com.business.finder.upload.application;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component("localPictureUploader")
class LocalPictureUploader implements PictureUploaderStrategy {

    @Override
    public PictureUploaderResponse upload(MultipartFile file, String fileName, String path) {
        Path root = Paths.get(path);
        try {
            Files.copy(file.getInputStream(), root.resolve(fileName + "." + StringUtils.getFilenameExtension(file.getOriginalFilename())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PictureUploaderResponse.OK;
    }
}
