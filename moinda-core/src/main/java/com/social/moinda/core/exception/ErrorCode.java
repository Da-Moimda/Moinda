package com.social.moinda.core.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_VALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, 400),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, 404);

    private HttpStatus status;
    private int code;
}
