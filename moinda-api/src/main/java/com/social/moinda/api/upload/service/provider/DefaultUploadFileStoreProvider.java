package com.social.moinda.api.upload.service.provider;

import com.social.moinda.api.upload.dto.UploadRequest;
import com.social.moinda.api.upload.service.UploadFileCommandService;
import com.social.moinda.core.domains.upload.dto.UploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultUploadFileStoreProvider implements UploadFileStoreProvider {

    @Value("${spring.servlet.multipart.location}")
    private String prefixPath;

    private static final String LOCAL_ROOT_PATH_PREFIX = "c:\\";

    private final UploadFileCommandService uploadFileCommandService;

    @Override
    @Transactional
    public List<UploadResponse> uploadFile(MultipartFile[] multipartFiles) {
        /**
         *  - 비교해야할 부분
         *  1. 파일의 크기가 얼마나 되는가, -> 스프링 부트에서 설정한 값을 따라서 체크를 한다. 2,3 번만 체크할 것.
         *  2. 파일의 확장자가 어떤 것인가.
         *  3. 업로드 하는 파일의 이름이 공백인지,
         */

        Date now = new Date();

        String formattedDate = getFormattedDate(now);

        doUploadFileValidation(multipartFiles);

        String directoryPath = makeDirectory(prefixPath, formattedDate);

        List<UploadResponse> uploadResponses = executeUpload(multipartFiles, directoryPath);

        return uploadResponses;
    }

    @Transactional
    public List<UploadResponse> executeUpload(MultipartFile[] multipartFiles, String directoryPath) {

        List<UploadResponse> responses = new LinkedList<>();

        for (MultipartFile multipartFile : multipartFiles) {

            String originalFilename = multipartFile.getOriginalFilename();

            String uuid = UUID.randomUUID().toString();
            String toSavedFullPath = LOCAL_ROOT_PATH_PREFIX + directoryPath + uuid + "_" + originalFilename;

            File toSaveFile = new File(toSavedFullPath);

            try {
                multipartFile.transferTo(toSaveFile);

                UploadRequest uploadRequest = new UploadRequest(directoryPath, originalFilename, uuid);

                UploadResponse uploadResponse = uploadFileCommandService.create(uploadRequest);

                responses.add(uploadResponse);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return responses;
    }

    private String makeDirectory(String... paths) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String path : paths) {
            stringBuilder
                    .append(path)
                    .append(File.separator);
        }

        String directoryPath = stringBuilder.toString();
        File file = new File(LOCAL_ROOT_PATH_PREFIX + directoryPath);

        if(!file.exists()) {
            file.mkdirs();
        }

        return directoryPath;
    }

    private String getFormattedDate(Date now) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(now);
    }

    private void doUploadFileValidation(MultipartFile[] multipartFiles) {
        for (MultipartFile multipartFile : multipartFiles) {

            String originalFilename = multipartFile.getOriginalFilename();

            if(originalFilename == null) {
                throw new IllegalStateException("파일이 없습니다.");
            }

            int separatorFileNameAndExtensionIndex = originalFilename.lastIndexOf(".");

            String fileExtension = originalFilename.substring(separatorFileNameAndExtensionIndex + 1);

            checkFileExtension(fileExtension);

            String fileName = originalFilename.substring(0, separatorFileNameAndExtensionIndex);

            if(fileName.isEmpty()) {
                throw new IllegalStateException("파일 이름이 없습니다.");
            }
        }
    }

    private void checkFileExtension(String fileExtension) {
        // TODO : 확장자 검사 임시설정
        if(!fileExtension.equalsIgnoreCase("png") && !fileExtension.equalsIgnoreCase("jpg")) {
            throw new IllegalStateException("확장자 에러");
        }
    }
}
