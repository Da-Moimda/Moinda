package com.social.moinda.api.group.exception;

import com.social.moinda.core.exception.ErrorCode;
import com.social.moinda.core.exception.ErrorResponse;

public class NotEnabledJoinStatusException extends RuntimeException {

    private static final String DEFAULT_ERROR_MESSAGE = "그룹 정원이 다 찼습니다.";

    public NotEnabledJoinStatusException() {
        this(DEFAULT_ERROR_MESSAGE);
    }

    public NotEnabledJoinStatusException(String message) {
        super(message);
    }

    public ErrorResponse getErrorResponse() {
        return ErrorCode.NOT_ENABLED_JOIN_STATUS.toEntityResponse(DEFAULT_ERROR_MESSAGE);
    }

}
