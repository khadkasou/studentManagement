package com.prakashmalla.sms.payload.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectRequest {

    private String name;
    private String description;
    private String subjectCode;
}
