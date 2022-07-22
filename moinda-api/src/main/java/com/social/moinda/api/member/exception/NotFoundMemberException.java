package com.social.moinda.api.member.exception;

public class NotFoundMemberException extends RuntimeException {

    public NotFoundMemberException() {
        this("등록되지 않은 사용자 입니다.");
    }

    public NotFoundMemberException(String message) {
        super(message);
    }

    public NotFoundMemberException(String message, Throwable cause) {
        super(message, cause);
    }
}
