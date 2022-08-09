package com.social.moinda.core.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ErrorResponse {

    private ErrorCode error;

    private int code;

    private String errorMessage;

    public ErrorResponse(ErrorCode error, int code, String errorMessage) {
        this.error = error;
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public ErrorResponse(ErrorCode error, int code, BindingResult bindingResult) {
        this.error = error;
        this.code = code;
        this.errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
    }
}
