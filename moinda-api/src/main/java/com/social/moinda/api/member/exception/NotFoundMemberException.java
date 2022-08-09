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

    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(
                ErrorCode.NOT_FOUND_MEMBER,
                DEFAULT_ERROR_MESSAGE
        );
    }
}
