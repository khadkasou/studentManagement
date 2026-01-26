package com.prakashmalla.sms.core.payload.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataPaginationResponse {
    public long totalElements;
    private Object result;
}
