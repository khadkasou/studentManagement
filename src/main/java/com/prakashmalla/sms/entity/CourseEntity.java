package com.prakashmalla.sms.entity;

import com.prakashmalla.sms.core.entity.BaseEntity;
import com.prakashmalla.sms.core.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "course")
public class CourseEntity extends BaseEntity {

    private String name;
    private String shortName;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    private String description;

}
