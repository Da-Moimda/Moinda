package com.social.moinda.api.member.exception;

public class RegisteredEmailException extends RuntimeException {
    public RegisteredEmailException() {
        this("등록된 이메일 입니다.");
    }

    public RegisteredEmailException(String message) {
        super(message);
    }

    public RegisteredEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
