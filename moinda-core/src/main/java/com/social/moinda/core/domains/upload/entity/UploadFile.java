package com.social.moinda.core.domains.upload.entity;

import com.social.moinda.core.BaseEntity;
import com.social.moinda.core.domains.upload.dto.UploadResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class UploadFile extends BaseEntity {

    @Column(nullable = false)
    private String directoryPath;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String uuid;

    public UploadFile(String directoryPath,
                      String originalFileName,
                      String uuid) {
        this.directoryPath = directoryPath;
        this.originalFileName = originalFileName;
        this.uuid = uuid;
    }

    public UploadResponse toUploadResponse() {
        return new UploadResponse(
                this.getId(),
                this.directoryPath,
                this.originalFileName,
                this.uuid
        );
    }
}
