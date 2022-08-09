package com.social.moinda.core.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public enum ErrorCode {

    // TODO : HttpStatus 사용을 유지할지
    //
    NOT_VALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, 400),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, 404),
    REGISTERED_MEMBER(HttpStatus.CONFLICT, 409),
    NOT_FOUND_GROUP(HttpStatus.NOT_FOUND, 404),
    NOT_FOUND_MEETING(HttpStatus.NOT_FOUND, 404),
    NOT_JOINED_GROUP_MEMBER(HttpStatus.NOT_FOUND, 404);

    private HttpStatus status;
    private int code;

    ErrorCode(HttpStatus status, int code) {
        this.status = status;
        this.code = code;
    }

    public static ErrorResponse notValidRequestResponse(BindingResult bindingResult) {
        return new ErrorResponse(
                NOT_VALID_REQUEST_BODY.status,
                NOT_VALID_REQUEST_BODY.code,
                bindingResult
        );
    }
}
