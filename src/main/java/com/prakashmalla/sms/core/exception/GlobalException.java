package com.prakashmalla.sms.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class GlobalException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;
    private final String debugMessage;

    public GlobalException(String message) {
        this.message = message;
        this.httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        this.debugMessage = "";
    }



    public GlobalException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.debugMessage = "";
    }


}
