package com.business.finder.upload.application.port;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

public interface LocalPictureUploaderUseCase {

    LocalPictureUploadedResponse uploadAndReplace(MultipartFile file, String fileName, String path);

    @Value
    class LocalPictureUploadedResponse {
        public static LocalPictureUploadedResponse OK = new LocalPictureUploadedResponse(true, Collections.emptyList());

        boolean success;
        List<String> errors; // should be enum, if we will use it.
    }

}
