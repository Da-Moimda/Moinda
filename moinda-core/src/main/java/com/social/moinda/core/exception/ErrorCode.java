package com.social.moinda.core.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public enum ErrorCode {

    // TODO : HttpStatus 사용을 유지할지
    NOT_VALID_REQUEST_BODY(1000),
    NOT_FOUND_MEMBER(2000),
    REGISTERED_MEMBER(2010),
    NOT_FOUND_GROUP(3000),
    REGISTERED_GROUP_NAME(3010),
    NOT_ENABLED_JOIN_STATUS(3020),
    NOT_FOUND_MEETING(4000),
    NOT_JOINED_GROUP_MEMBER(5000),
    ALREADY_JOIN_GROUP_MEMBER(5010),
    NOT_ALLOW_FILE_EXTENSION(6000),
    EMPTY_UPLOAD_FILE(6010),
    EMPTY_UPLOAD_FILE_NAME(6020),
    ;

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public static ErrorResponse notValidRequestResponse(BindingResult bindingResult) {
        return NOT_VALID_REQUEST_BODY.toEntityResponse(bindingResult);
    }

    public ErrorResponse toEntityResponse(final String ERROR_MESSAGE) {
        return new ErrorResponse(
                this,
                this.code,
                ERROR_MESSAGE
        );
    }

    public ErrorResponse toEntityResponse(BindingResult bindingResult) {
        return new ErrorResponse(
                this,
                this.code,
                bindingResult
        );
    }
}
