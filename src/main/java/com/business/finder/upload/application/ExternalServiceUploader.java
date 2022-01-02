package com.business.finder.upload.application;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("externalServiceUploader")
class ExternalServiceUploader implements PictureUploaderStrategy {

    @Override
    public PictureUploaderResponse upload(MultipartFile file, String fileName, String path) {
        throw new NotYetImplementedException("ExternalServiceUploader strategy is only template. It's not implemented at this moment.");
    }

}
