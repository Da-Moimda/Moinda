package com.social.moinda.core.domains.upload.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UploadResponse {

    private Long id;

    private String directoryPath;

    private String originalFileName;

    private String uuid;

    public UploadResponse(Long id,
                          String directoryPath,
                          String originalFileName,
                          String uuid) {
        this.id = id;
        this.directoryPath = directoryPath;
        this.originalFileName = originalFileName;
        this.uuid = uuid;
    }
}
