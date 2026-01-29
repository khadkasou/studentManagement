package com.prakashmalla.sms.entity;

import com.prakashmalla.sms.core.entity.BaseEntity;
import com.prakashmalla.sms.core.enums.StatusEnum;
import com.prakashmalla.sms.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "student")
public class StudentEntity extends BaseEntity {

    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date dateOfBirth;

    private String nationality;
    private String citizenshipNumber;

    private String guardianName;
    private String guardianContactNumber;
    private Date enrollmentDate;

    @Column(nullable = false, unique = true, length = 50)
    private String studentCode;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "temporary_address_id")
    private AddressEntity temporaryAddress;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "permanent_address_id")
    private AddressEntity permanentAddress;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "course_id")
    private CourseEntity course;

}
