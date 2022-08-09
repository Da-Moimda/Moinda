package com.social.moinda.web.member.advice;

import com.social.moinda.api.member.exception.NotFoundMemberException;
import com.social.moinda.api.member.exception.RegisteredEmailException;
import com.social.moinda.core.exception.ErrorCode;
import com.social.moinda.core.exception.ErrorResponse;
import com.social.moinda.web.member.MemberApiController;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = MemberApiController.class)
public class MemberApiControllerAdvice {

    // TODO : 전체적으로 쓸지
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestBodyHandler(BindingResult bindingResult) {
        return ErrorCode.notValidRequestResponse(bindingResult);
    }

    @ExceptionHandler(value = NotFoundMemberException.class)
    public ErrorResponse notFoundMemberExceptionHandler(NotFoundMemberException ex) {
        return ex.getErrorResponse();
    }

    @ExceptionHandler(value = RegisteredEmailException.class)
    public ErrorResponse registeredEmailExceptionHandler(RegisteredEmailException ex) {
        return ex.getErrorResponse();
    }

}
