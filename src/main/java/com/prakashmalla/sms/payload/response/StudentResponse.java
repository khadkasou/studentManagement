package com.prakashmalla.sms.payload.response;

import com.prakashmalla.sms.enums.GenderEnum;
import com.prakashmalla.sms.enums.StatusEnum;
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
    private Date dateOfBirth;
    private String nationality;
    private String citizenshipNumber;
    private String guardianName;
    private String guardianContactNumber;
    private Date enrollmentDate;
    private String studentCode;
    private GenderEnum gender;
    private StatusEnum status;
    private AddressResponse temporaryAddress;
    private AddressResponse permanentAddress;
    private List<CourseResponse> courses;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
