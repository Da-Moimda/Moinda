package com.social.moinda.api.member.exception;

import com.social.moinda.core.exception.ErrorCode;
import com.social.moinda.core.exception.ErrorResponse;

public class NotFoundMemberException extends RuntimeException {
    private static final String DEFAULT_ERROR_MESSAGE = "등록되지 않은 사용자 입니다.";

    public NotFoundMemberException() {
        this(DEFAULT_ERROR_MESSAGE);
    }

    public NotFoundMemberException(String message) {
        super(message);
    }

    public ErrorResponse getResponse() {
        return new ErrorResponse(ErrorCode.NOT_VALID_REQUEST_BODY, DEFAULT_ERROR_MESSAGE);
    }
}
