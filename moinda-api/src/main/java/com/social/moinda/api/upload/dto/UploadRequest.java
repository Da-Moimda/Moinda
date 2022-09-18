package com.social.moinda.api.upload.dto;

import com.social.moinda.core.domains.upload.entity.UploadFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UploadRequest {

    private String directoryPath;

    private String originalFileName;

    private String uuid;

    public UploadRequest(String directoryPath,
                         String originalFileName,
                         String uuid) {
        this.directoryPath = directoryPath;
        this.originalFileName = originalFileName;
        this.uuid = uuid;
    }

    public UploadFile bindEntity() {
        return new UploadFile(
                this.directoryPath,
                this.originalFileName,
                this.uuid
        );
    }
}
