package com.social.moinda.api.group.exception;

public class NotFoundGroupException extends RuntimeException {
    public NotFoundGroupException() {
        this("존재하지 않는 그룹입니다.");
    }

    public NotFoundGroupException(String message) {
        super(message);
    }
}
