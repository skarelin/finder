package com.business.finder.upload.application.port;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

public interface LocalPictureUploaderUseCase {

    Response upload(MultipartFile file, String fileName, String path);

    @Value
    class Response {
        public static Response OK = new Response(true, Collections.emptyList());

        boolean success;
        List<String> errors; // should be enum, if we will use it.
    }

}
