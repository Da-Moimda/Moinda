package com.social.moinda.web.upload;

import com.social.moinda.api.upload.service.provider.UploadFileStoreProvider;
import com.social.moinda.core.domains.upload.dto.UploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadApiController {

    private final UploadFileStoreProvider uploadFileStoreProvider;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UploadResponse> upload(@RequestPart("files") MultipartFile[] multipartFiles) {
        UploadResponse uploadResponse = uploadFileStoreProvider.uploadFile(multipartFiles);
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadResponse);
    }

}
