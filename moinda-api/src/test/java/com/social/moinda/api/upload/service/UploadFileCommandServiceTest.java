package com.social.moinda.api.upload.service;

import com.social.moinda.api.upload.dto.UploadRequest;
import com.social.moinda.core.domains.upload.dto.UploadResponse;
import com.social.moinda.core.domains.upload.entity.UploadFile;
import com.social.moinda.core.domains.upload.entity.UploadFileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
class UploadFileCommandServiceTest {

    private static final String DIRECTORY_PATH = "c:\\moinda\\files\\";

    @Mock
    private UploadFileRepository uploadFileRepository;

    @InjectMocks
    private UploadFileCommandService uploadFileCommandService;

    private final String UUID_S = UUID.randomUUID().toString();
    private final String ORIGINAL_FILE_NAME = "dogs.jpg";
    private final UploadRequest UPLOAD_REQUEST = new UploadRequest(DIRECTORY_PATH, ORIGINAL_FILE_NAME, UUID_S);

    @DisplayName("파일을 업로드해서 저장에 성공한다.")
    @Test
    void createSuccessTest() {

        UploadFile entity = UPLOAD_REQUEST.bindEntity();

        given(uploadFileRepository.save(any(UploadFile.class))).willReturn(entity);

        UploadResponse uploadResponse = uploadFileCommandService.create(UPLOAD_REQUEST);

        then(uploadFileRepository).should(times(1)).save(any(UploadFile.class));

        assertAll(
                () -> assertThat(uploadResponse).isNotNull()
        );
    }

}