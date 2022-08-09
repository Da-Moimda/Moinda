package com.social.moinda.api.group.exception;

import com.social.moinda.core.exception.ErrorCode;
import com.social.moinda.core.exception.ErrorResponse;

public class NotFoundGroupException extends RuntimeException {

    private static final String DEFAULT_ERROR_MESSAGE = "존재하지 않는 그룹입니다.";

    public NotFoundGroupException() {
        this(DEFAULT_ERROR_MESSAGE);
    }

    public NotFoundGroupException(String message) {
        super(message);
    }

    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(
                ErrorCode.NOT_FOUND_GROUP,
                DEFAULT_ERROR_MESSAGE
        );
    }
}
