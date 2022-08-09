package com.social.moinda.api.meeting.exception;

import com.social.moinda.core.exception.ErrorCode;
import com.social.moinda.core.exception.ErrorResponse;

public class NotFoundMeetingException extends RuntimeException {

    private static final String DEFAULT_ERROR_MESSAGE = "존재하지 않는 모임입니다.";

    public NotFoundMeetingException() {
        this(DEFAULT_ERROR_MESSAGE);
    }

    public NotFoundMeetingException(String message) {
        super(message);
    }

    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(
                ErrorCode.NOT_FOUND_MEETING,
                DEFAULT_ERROR_MESSAGE
        );
    }
}
