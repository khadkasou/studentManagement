package com.prakashmalla.sms.core.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PaginationRequest {
    private int pageNo;
    private int pageSize;
    private String sortBy;
    private String direction;
}