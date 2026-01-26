package com.prakashmalla.sms.core.payload.response;


import com.prakashmalla.sms.core.exception.GlobalException;

public class GlobalResponseBuilder {

    private GlobalResponseBuilder() {
    }

    public static GlobalResponse buildSuccessResponse(String message) {
        return GlobalResponse.builder()
                .status(true)
                .message(message)
                .build();
    }
    public static GlobalResponse buildSuccessResponseWithData(String message, Object object) {
        return GlobalResponse.builder()
                .status(true)
                .message(message)
                .data(object)
                .build();
    }

    public static GlobalResponse buildFailResponse(GlobalException e) {
        return GlobalResponse.builder()
                .status(false)
                .message(e.getMessage())
                .build();
    }

    public static GlobalResponse buildUnknownFailResponse(Exception e) {
        return GlobalResponse.builder()
                .status(false)
                .message(e.getMessage())
                .build();
    }
}
