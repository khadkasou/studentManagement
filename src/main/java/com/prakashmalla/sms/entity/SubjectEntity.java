package com.prakashmalla.sms.entity;

import com.prakashmalla.sms.core.entity.BaseEntity;
import com.prakashmalla.sms.core.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "subject")
public class SubjectEntity extends BaseEntity {

    private String name;
    private String description;

    private String subjectCode;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

}
