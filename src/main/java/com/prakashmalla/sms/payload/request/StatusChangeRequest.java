package com.prakashmalla.sms.payload.request;


import com.prakashmalla.sms.core.enums.StatusEnum;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusChangeRequest {

    private StatusEnum status;


}
