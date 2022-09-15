package com.social.moinda.api.upload.service.provider;

import com.social.moinda.core.domains.upload.dto.UploadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DefaultUploadFileStoreProvider implements UploadFileStoreProvider {
    @Override
    public UploadResponse uploadFile(MultipartFile[] multipartFiles) {
        return null;
    }
}
