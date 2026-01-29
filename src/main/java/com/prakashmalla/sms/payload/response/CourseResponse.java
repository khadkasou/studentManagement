package com.prakashmalla.sms.payload.response;

import com.prakashmalla.sms.core.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    private Long id;
    private String name;
    private String shortName;
    private String description;
    private String status;
    private List<SubjectResponse> subjects;


}
