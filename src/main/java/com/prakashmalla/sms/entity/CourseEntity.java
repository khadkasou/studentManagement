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
@Table(name = "course")
public class CourseEntity extends BaseEntity {

    private String name;
    private String shortName;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    private String description;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "subject_course", joinColumns = @JoinColumn(name = "course_id"),inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<SubjectEntity> subject;


}
