package com.business.finder.upload.application;

import com.business.finder.upload.application.exception.UploadPictureException;
import com.business.finder.upload.application.port.LocalPictureUploaderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class LocalPictureUploaderService implements LocalPictureUploaderUseCase {

    private final PictureUploaderStrategy localPictureUploader;

    @Override
    public LocalPictureUploadedResponse uploadAndReplace(MultipartFile file, String fileName, String path) {
        PictureUploaderResponse response = localPictureUploader.uploadAndReplace(file, fileName, path);
        if (response.getErrors().isEmpty()) {
            return LocalPictureUploadedResponse.OK;
        } else {
            throw new UploadPictureException("Unhandled business exception during uploading local picture: " + response.getErrors().get(0));
        }
    }

}
