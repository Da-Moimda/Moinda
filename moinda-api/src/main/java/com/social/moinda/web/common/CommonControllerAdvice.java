package com.social.moinda.web.common;


import com.social.moinda.core.exception.ErrorCode;
import com.social.moinda.core.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> invalidRequestBodyHandler(BindingResult bindingResult) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorCode.notValidRequestResponse(bindingResult));
    }

}
