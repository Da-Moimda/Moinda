package com.social.moinda.api.upload.exception;

import com.social.moinda.core.exception.ErrorCode;
import com.social.moinda.core.exception.ErrorResponse;

public class NotAllowFileExtensionException extends RuntimeException {
    private static final String DEFAULT_ERROR_MESSAGE = "허용되지 않는 확장자 파일입니다.";

    public NotAllowFileExtensionException() {
        this(DEFAULT_ERROR_MESSAGE);
    }

    public NotAllowFileExtensionException(String message) {
        super(message);
    }

    public ErrorResponse getErrorResponse() {
        return ErrorCode.NOT_ALLOW_FILE_EXTENSION.toEntityResponse(DEFAULT_ERROR_MESSAGE);
    }
}
