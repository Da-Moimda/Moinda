package com.social.moinda.api.meeting.exception;

public class NotFoundMeetingException extends RuntimeException {

    public NotFoundMeetingException() {
        this("존재하지 않는 모임입니다.");
    }

    public NotFoundMeetingException(String message) {
        super(message);
    }
}
