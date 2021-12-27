package com.business.finder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class ApplicationVersionController {

    @Value("${bf.application.version}")
    private String appVersion;

    @GetMapping
    public ResponseEntity<String> getApplicationVersion() {
        return ResponseEntity.ok(appVersion);
    }
}
