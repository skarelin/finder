package com.business.finder.upload.application;

import org.springframework.web.multipart.MultipartFile;

public interface PictureUploaderStrategy {
    PictureUploaderResponse upload(MultipartFile file, String fileName, String path);
}
