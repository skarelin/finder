package com.business.finder.upload.application;


import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
public class PictureUploaderResponse {
    public static PictureUploaderResponse OK = new PictureUploaderResponse(true, Collections.emptyList());

    boolean success;
    List<String> errors; // should be enum, if it will be usable.
}