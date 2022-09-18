package com.social.moinda.api.upload.service.provider;

import com.social.moinda.core.domains.upload.dto.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadFileStoreProvider {
    List<UploadResponse> uploadFile(MultipartFile[] multipartFiles);
}
