package com.social.moinda.api.upload.exception;

import com.social.moinda.core.exception.ErrorCode;
import com.social.moinda.core.exception.ErrorResponse;

public class EmptyUploadFileNameException extends RuntimeException {
    private static final String DEFAULT_ERROR_MESSAGE = "업로드하는 파일의 이름이 비어있습니다.";

    public EmptyUploadFileNameException() {
        this(DEFAULT_ERROR_MESSAGE);
    }

    public EmptyUploadFileNameException(String message) {
        super(message);
    }

    public ErrorResponse getErrorResponse() {
        return ErrorCode.EMPTY_UPLOAD_FILE_NAME.toEntityResponse(DEFAULT_ERROR_MESSAGE);
    }
}
