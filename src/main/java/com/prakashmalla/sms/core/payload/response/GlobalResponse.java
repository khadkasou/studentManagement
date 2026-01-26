package com.prakashmalla.sms.core.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public  class GlobalResponse {
    private boolean status;
    private String code;
    private Object data;
    private String message;
}
