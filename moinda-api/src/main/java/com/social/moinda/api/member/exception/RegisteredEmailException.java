package com.social.moinda.api.member.exception;

import com.social.moinda.core.exception.ErrorCode;
import com.social.moinda.core.exception.ErrorResponse;

public class RegisteredEmailException extends RuntimeException {

    private static final String DEFAULT_ERROR_MESSAGE = "등록된 이메일 입니다.";

    public RegisteredEmailException() {
        this(DEFAULT_ERROR_MESSAGE);
    }

    public RegisteredEmailException(String message) {
        super(message);
    }

    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(
                ErrorCode.REGISTERED_MEMBER,
                DEFAULT_ERROR_MESSAGE
        );
    }
}
