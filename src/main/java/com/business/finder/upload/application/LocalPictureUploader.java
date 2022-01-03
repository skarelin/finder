package com.business.finder.upload.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component("localPictureUploader")
@Slf4j
class LocalPictureUploader implements PictureUploaderStrategy {

    @Override
    public PictureUploaderResponse upload(MultipartFile file, String fileName, String path) {
        Path root = Paths.get(path);
        try {
            Files.copy(file.getInputStream(), root.resolve(fileName + "." + StringUtils.getFilenameExtension(file.getOriginalFilename())));
            log.info("Local picture storage: uploaded file with name " + fileName);
        } catch (Exception e) {
            throw new RuntimeException("Exception during uploading picture for local storage. Stacktrace: " + e);
        }
        return PictureUploaderResponse.OK;
    }
}
