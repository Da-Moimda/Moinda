package com.social.moinda.web.upload;

import com.social.moinda.api.upload.service.provider.UploadFileStoreProvider;
import com.social.moinda.web.BaseApiConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UploadApiController.class)
class UploadApiControllerTest extends BaseApiConfig {

    @MockBean
    private UploadFileStoreProvider uploadFileStoreProvider;

    private static final String UPLOAD_API_URI = "/api/upload";
    private static final String UPLOAD_REQUEST_PART_KEY = "files";
    private static final String UPLOAD_ORIGINAL_FILE_NAME = "filter.jpg";
    private static final String UPLOAD_CONTENT_TYPE = "jpeg/image";

    private static final MockMultipartFile MOCK_MULTIPART_FILE = new MockMultipartFile(UPLOAD_REQUEST_PART_KEY,
            UPLOAD_ORIGINAL_FILE_NAME,
            UPLOAD_CONTENT_TYPE,
            (byte[]) null);

    @DisplayName("파일 업로드를 성공한다.")
    @Test
    void post_upload() throws Exception {

        ResultActions perform = mockMvc.perform(multipart(UPLOAD_API_URI)
                .file(MOCK_MULTIPART_FILE)
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isCreated());
    }
}