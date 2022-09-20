package com.social.moinda.web.upload.advice;

import com.social.moinda.api.upload.exception.EmptyUploadFileException;
import com.social.moinda.api.upload.exception.EmptyUploadFileNameException;
import com.social.moinda.api.upload.exception.NotAllowFileExtensionException;
import com.social.moinda.core.exception.ErrorResponse;
import com.social.moinda.web.upload.UploadApiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = UploadApiController.class)
public class UploadApiControllerAdvice {

    @ExceptionHandler(value = EmptyUploadFileException.class)
    public ResponseEntity<ErrorResponse> notFoundMeetingExceptionHandler(EmptyUploadFileException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorResponse());
    }

    @ExceptionHandler(value = EmptyUploadFileNameException.class)
    public ResponseEntity<ErrorResponse> notFoundGroupExceptionHandler(EmptyUploadFileNameException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorResponse());
    }

    @ExceptionHandler(value = NotAllowFileExtensionException.class)
    public ResponseEntity<ErrorResponse> notFoundGroupExceptionHandler(NotAllowFileExtensionException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorResponse());
    }
}
