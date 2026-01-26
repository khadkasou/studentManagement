package com.prakashmalla.sms.payload.response;

import com.prakashmalla.sms.core.enums.StatusEnum;
import com.prakashmalla.sms.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private String nationality;
    private String citizenshipNumber;
    private String guardianName;
    private String guardianContactNumber;
    private String enrollmentDate;
    private String studentCode;
    private String gender;
    private String status;
    private AddressResponse temporaryAddress;
    private AddressResponse permanentAddress;
    private CourseResponse course;
}
