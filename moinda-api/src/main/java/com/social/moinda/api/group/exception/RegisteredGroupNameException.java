package com.social.moinda.api.group.exception;

public class RegisteredGroupNameException extends RuntimeException {

    public RegisteredGroupNameException() {
        this("등록된 그룹명 입니다.");
    }

    public RegisteredGroupNameException(String message) {
        super(message);
    }

    public RegisteredGroupNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
