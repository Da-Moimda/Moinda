package com.social.moinda.api.upload.service;

import com.social.moinda.api.upload.dto.UploadRequest;
import com.social.moinda.core.domains.upload.dto.UploadResponse;
import com.social.moinda.core.domains.upload.entity.UploadFile;
import com.social.moinda.core.domains.upload.entity.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UploadFileCommandService {

    private final UploadFileRepository uploadFileRepository;

    public UploadResponse create(UploadRequest uploadRequest) {
        UploadFile entity = uploadRequest.bindEntity();

        UploadFile savedEntity = uploadFileRepository.save(entity);

        return savedEntity.toUploadResponse();
    }
}
