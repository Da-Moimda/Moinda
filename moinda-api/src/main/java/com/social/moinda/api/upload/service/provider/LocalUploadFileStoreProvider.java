package com.social.moinda.api.upload.service.provider;

import com.social.moinda.api.upload.dto.UploadRequest;
import com.social.moinda.api.upload.service.UploadFileCommandService;
import com.social.moinda.core.domains.upload.dto.UploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service(value = "localUploadStoreProvider")
@RequiredArgsConstructor
public class LocalUploadFileStoreProvider extends AbstractUploadFileStoreProvider {

    private static final String LOCAL_ROOT_PATH_PREFIX = "c:\\";
    private static final String UNDER_BAR_SEPARATOR = "_";
    private final UploadFileCommandService uploadFileCommandService;

    @Override
    public List<UploadResponse> executeUpload(MultipartFile[] multipartFiles, String directoryPath) {

        List<UploadResponse> responses = new LinkedList<>();

        for (MultipartFile multipartFile : multipartFiles) {

            String originalFilename = multipartFile.getOriginalFilename();

            String uuid = UUID.randomUUID().toString();
            String toSavedFullPath = combineDirectoryPath(LOCAL_ROOT_PATH_PREFIX,
                    directoryPath,
                    uuid,
                    UNDER_BAR_SEPARATOR,
                    originalFilename);

            File toSaveFile = new File(toSavedFullPath);

            try {
                multipartFile.transferTo(toSaveFile);

                UploadRequest uploadRequest = new UploadRequest(directoryPath, originalFilename, uuid);

                UploadResponse uploadResponse = uploadFileCommandService.create(uploadRequest);

                responses.add(uploadResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return responses;
    }

    @Override
    public String makeDirectory(String... fragments) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String fragment : fragments) {
            stringBuilder
                    .append(fragment)
                    .append(File.separator);
        }

        String directoryPath = stringBuilder.toString();

        File file = new File(combineDirectoryPath(LOCAL_ROOT_PATH_PREFIX, directoryPath));

        if (!file.exists()) {
            file.mkdirs();
        }

        return directoryPath;
    }
}
