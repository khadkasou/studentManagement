package com.prakashmalla.sms.payload.request;

import com.prakashmalla.sms.enums.GenderEnum;
import com.prakashmalla.sms.enums.StatusEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    private String phoneNumber;

    private Date dateOfBirth;

    private String nationality;

    private String citizenshipNumber;

    private String guardianName;

    private String guardianContactNumber;

    private Date enrollmentDate;

    @NotNull(message = "Gender is required")
    private GenderEnum gender;

    private StatusEnum status;

    private AddressRequest temporaryAddress;

    private AddressRequest permanentAddress;

    private List<Long> courseIds;
}
