package com.social.moinda.api.upload.exception;

import com.social.moinda.core.exception.ErrorCode;
import com.social.moinda.core.exception.ErrorResponse;

public class EmptyUploadFileException extends RuntimeException {
    private static final String DEFAULT_ERROR_MESSAGE = "파일이 없습니다.";

    public EmptyUploadFileException() {
        this(DEFAULT_ERROR_MESSAGE);
    }

    public EmptyUploadFileException(String message) {
        super(message);
    }

    public ErrorResponse getErrorResponse() {
        return ErrorCode.EMPTY_UPLOAD_FILE.toEntityResponse(DEFAULT_ERROR_MESSAGE);
    }
}
