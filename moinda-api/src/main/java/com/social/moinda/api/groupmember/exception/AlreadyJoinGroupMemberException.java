package com.social.moinda.api.groupmember.exception;

import com.social.moinda.core.exception.ErrorCode;
import com.social.moinda.core.exception.ErrorResponse;

public class AlreadyJoinGroupMemberException extends RuntimeException {

    private static final String DEFAULT_ERROR_MESSAGE = "이미 가입한 그룹입니다.";

    public AlreadyJoinGroupMemberException() {
        this(DEFAULT_ERROR_MESSAGE);
    }

    public AlreadyJoinGroupMemberException(String message) {
        super(message);
    }

    public ErrorResponse getErrorResponse() {
        return ErrorCode.ALREADY_JOIN_GROUP_MEMBER.toEntityResponse(DEFAULT_ERROR_MESSAGE);
    }
}
