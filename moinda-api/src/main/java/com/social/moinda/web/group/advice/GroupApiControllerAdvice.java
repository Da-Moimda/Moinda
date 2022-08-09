package com.social.moinda.web.group.advice;

import com.social.moinda.api.group.exception.NotEnabledJoinStatusException;
import com.social.moinda.api.group.exception.NotFoundGroupException;
import com.social.moinda.api.group.exception.RegisteredGroupNameException;
import com.social.moinda.api.groupmember.exception.AlreadyJoinGroupMemberException;
import com.social.moinda.api.member.exception.NotFoundMemberException;
import com.social.moinda.core.exception.ErrorCode;
import com.social.moinda.core.exception.ErrorResponse;
import com.social.moinda.web.group.GroupApiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = GroupApiController.class)
public class GroupApiControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestBodyHandler(BindingResult bindingResult) {
        return ErrorCode.notValidRequestResponse(bindingResult);
    }

    @ExceptionHandler(value = NotFoundGroupException.class)
    public ResponseEntity<ErrorResponse> notFoundGroupExceptionHandler(NotFoundGroupException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
    }

    @ExceptionHandler(value = NotFoundMemberException.class)
    public ResponseEntity<ErrorResponse> notFoundMemberExceptionHandler(NotFoundMemberException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
    }

    @ExceptionHandler(value = NotEnabledJoinStatusException.class)
    public ResponseEntity<ErrorResponse> notEnabledJoinStatusExceptionHandler(NotEnabledJoinStatusException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getErrorResponse());
    }

    @ExceptionHandler(value = RegisteredGroupNameException.class)
    public ResponseEntity<ErrorResponse> registeredGroupNameExceptionHandler(RegisteredGroupNameException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getErrorResponse());
    }

    @ExceptionHandler(value = AlreadyJoinGroupMemberException.class)
    public ResponseEntity<ErrorResponse> alreadyJoinGroupMemberExceptionHandler(AlreadyJoinGroupMemberException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getErrorResponse());
    }

}
