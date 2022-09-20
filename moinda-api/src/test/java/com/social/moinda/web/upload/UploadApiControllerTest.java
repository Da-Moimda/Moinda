package com.social.moinda.web.upload;

import com.social.moinda.api.upload.service.provider.UploadFileStoreProvider;
import com.social.moinda.web.BaseApiConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;

import static com.social.moinda.web.RestDocsConfig.field;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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

    @Disabled
    @DisplayName("파일 업로드를 성공한다.")
    @Test
    void post_upload() throws Exception {

        ResultActions perform = mockMvc.perform(multipart(UPLOAD_API_URI)
                .file(MOCK_MULTIPART_FILE)
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isCreated())
                .andDo(restDocs.document(
                                requestFields(
                                        fieldWithPath("name").description("요청시 키값").attributes(field("constraint", "key")),
                                        fieldWithPath("originalFileName").description("파일명").attributes(field("constraint", "파일명+확장자포함")),
                                        fieldWithPath("contentType").description("파일타입").attributes(field("constraint", "Only Multipart-Type")),
                                        fieldWithPath("content").description("미사용").attributes(field("constraint", "null 가능"))
                                ),
                                responseFields(
                                        fieldWithPath("uploadResponse[].id").description("파일번호"),
                                        fieldWithPath("uploadResponse[].directoryPath").description("저장경로"),
                                        fieldWithPath("uploadResponse[].originalFileName").description("본래파일명"),
                                        fieldWithPath("uploadResponse[].uuid").description("파일별칭")
                                )
                        )
                );
    }
}