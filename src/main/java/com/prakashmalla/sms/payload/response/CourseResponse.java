package com.prakashmalla.sms.payload.response;

import com.prakashmalla.sms.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    private Long id;
    private String name;
    private StatusEnum status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
