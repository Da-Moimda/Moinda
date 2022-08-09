package com.social.moinda.api.groupmember.exception;

import com.social.moinda.core.exception.ErrorCode;
import com.social.moinda.core.exception.ErrorResponse;

public class NotJoinedGroupMemberException extends RuntimeException {

    private static final String DEFAULT_ERROR_MESSAGE = "해당 그룹에 가입한 사용자가 아닙니다.";

    public NotJoinedGroupMemberException() {
        this(DEFAULT_ERROR_MESSAGE);
    }

    public NotJoinedGroupMemberException(String message) {
        super(message);
    }

    public ErrorResponse getErrorResponse() {
        return ErrorCode.NOT_JOINED_GROUP_MEMBER.toEntityResponse(DEFAULT_ERROR_MESSAGE);
    }
}
