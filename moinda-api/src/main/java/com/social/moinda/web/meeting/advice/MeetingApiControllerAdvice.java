package com.social.moinda.web.meeting.advice;

import com.social.moinda.api.group.exception.NotFoundGroupException;
import com.social.moinda.api.groupmember.exception.NotJoinedGroupMemberException;
import com.social.moinda.api.meeting.exception.NotFoundMeetingException;
import com.social.moinda.core.exception.ErrorResponse;
import com.social.moinda.web.meeting.MeetingApiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = MeetingApiController.class)
public class MeetingApiControllerAdvice {

    @ExceptionHandler(value = NotFoundMeetingException.class)
    public ResponseEntity<ErrorResponse> notFoundMeetingExceptionHandler(NotFoundMeetingException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
    }

    @ExceptionHandler(value = NotFoundGroupException.class)
    public ResponseEntity<ErrorResponse> notFoundGroupExceptionHandler(NotFoundGroupException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
    }

    @ExceptionHandler(value = NotJoinedGroupMemberException.class)
    public ResponseEntity<ErrorResponse> notFoundGroupExceptionHandler(NotJoinedGroupMemberException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
    }

}
