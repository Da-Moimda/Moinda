package com.social.moinda.core.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ErrorResponse {

    private HttpStatus status;
    private int errorCode;
    private String errorMessage;

    public ErrorResponse(ErrorCode errorCode, String errorMessage) {
        this.status = errorCode.getStatus();
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorMessage;
    }

    public ErrorResponse(HttpStatus status, int errorCode, BindingResult bindingResult) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = bindingResult.getFieldError().getDefaultMessage();
    }
}
