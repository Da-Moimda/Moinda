package com.social.moinda.api.group.exception;

import com.social.moinda.core.exception.ErrorCode;
import com.social.moinda.core.exception.ErrorResponse;

public class RegisteredGroupNameException extends RuntimeException {

    private static final String DEFAULT_ERROR_MESSAGE = "등록된 그룹명 입니다.";

    public RegisteredGroupNameException() {
        this(DEFAULT_ERROR_MESSAGE);
    }

    public RegisteredGroupNameException(String message) {
        super(message);
    }

    public ErrorResponse getErrorResponse() {
        return ErrorCode.REGISTERED_GROUP_NAME.toEntityResponse(DEFAULT_ERROR_MESSAGE);
    }
}