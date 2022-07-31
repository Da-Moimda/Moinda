package com.social.moinda.api.group.exception;

public class NotEnabledJoinStatusException extends RuntimeException {
    public NotEnabledJoinStatusException() {
        this("그룹 정원이 다 찼습니다.");
    }

    public NotEnabledJoinStatusException(String message) {
        super(message);
    }
}
