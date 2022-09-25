package com.social.moinda.web.member.advice;

import com.social.moinda.api.member.exception.NotFoundMemberException;
import com.social.moinda.api.member.exception.RegisteredEmailException;
import com.social.moinda.core.exception.ErrorResponse;
import com.social.moinda.web.member.MemberApiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = MemberApiController.class)
public class MemberApiControllerAdvice {

    @ExceptionHandler(value = NotFoundMemberException.class)
    public ResponseEntity<ErrorResponse> notFoundMemberExceptionHandler(NotFoundMemberException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
    }

    @ExceptionHandler(value = RegisteredEmailException.class)
    public ResponseEntity<ErrorResponse> registeredEmailExceptionHandler(RegisteredEmailException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorResponse());
    }

}
