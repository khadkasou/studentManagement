package com.prakashmalla.sms.core.payload.response;


import com.prakashmalla.sms.core.enums.ApiStatusEnum;
import com.prakashmalla.sms.core.exception.GlobalException;
import com.prakashmalla.sms.core.util.MessageBundle;

public class GlobalResponseBuilder {

    private GlobalResponseBuilder() {
    }

    public static GlobalResponse buildSuccessResponse(String code) {
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .code(code)
                .message(MessageBundle.getSuccessMessage(code))
                .build();
    }

    public static GlobalResponse buildSuccessResponseWithMessage(String code, String message) {
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .message(message)
                .code(code)
                .build();
    }
    public static GlobalResponse buildSuccessResponse(String code, Object object) {
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .data(object)
                .code(code)
                .message(MessageBundle.getSuccessMessage(code))
                .build();
    }
    public static GlobalResponse buildSuccessResponseWithData(String code, String message, Object object) {
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .code(code)
                .message(message)
                .data(object)
                .build();
    }

    public static GlobalResponse buildFailResponse(GlobalException e) {
        return GlobalResponse.builder()
                .status(ApiStatusEnum.FAILED)
                .message(e.getMessage())
                .code(e.getCode())
                .build();
    }

    public static GlobalResponse buildUnknownFailResponse(Exception e) {
        return GlobalResponse.builder()
                .status(ApiStatusEnum.FAILED)
                .message(e.getMessage())
                .build();
    }

    public static GlobalResponse buildPendingResponse(GlobalException e) {
        return GlobalResponse.builder()
                .status(ApiStatusEnum.PENDING)
                .message(e.getMessage())
                .build();
    }

}
