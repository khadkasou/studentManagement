package com.prakashmalla.sms.payload.request;

import com.prakashmalla.sms.core.payload.request.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDataRequest extends PaginationRequest {
    private String searchText;
}
