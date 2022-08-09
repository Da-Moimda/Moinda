package com.social.moinda.web.meeting.advice;

import com.social.moinda.api.group.exception.NotFoundGroupException;
import com.social.moinda.api.groupmember.exception.NotJoinedGroupMemberException;
import com.social.moinda.api.meeting.exception.NotFoundMeetingException;
import com.social.moinda.core.exception.ErrorCode;
import com.social.moinda.core.exception.ErrorResponse;
import com.social.moinda.web.meeting.MeetingApiController;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = MeetingApiController.class)
public class MeetingApiControllerAdvice {

    // TODO : 전체적으로 쓸지
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestBodyHandler(BindingResult bindingResult) {
        return ErrorCode.notValidRequestResponse(bindingResult);
    }

    @ExceptionHandler(value = NotFoundMeetingException.class)
    public ErrorResponse notFoundMeetingExceptionHandler(NotFoundMeetingException ex) {
        return ex.getErrorResponse();
    }

    @ExceptionHandler(value = NotFoundGroupException.class)
    public ErrorResponse notFoundGroupExceptionHandler(NotFoundGroupException ex) {
        return ex.getErrorResponse();
    }

    @ExceptionHandler(value = NotJoinedGroupMemberException.class)
    public ErrorResponse notFoundGroupExceptionHandler(NotJoinedGroupMemberException ex) {
        return ex.getErrorResponse();
    }

}
