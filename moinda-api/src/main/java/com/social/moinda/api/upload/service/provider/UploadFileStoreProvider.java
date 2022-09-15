package com.social.moinda.api.upload.service.provider;

import com.social.moinda.core.domains.upload.dto.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileStoreProvider {
    UploadResponse uploadFile(MultipartFile[] multipartFiles);
}
