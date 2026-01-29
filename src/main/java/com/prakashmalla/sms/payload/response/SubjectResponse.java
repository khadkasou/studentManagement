package com.prakashmalla.sms.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectResponse {

    private Long id;
    private String name;
    private String description;
    private String subjectCode;
    private String status;
}
