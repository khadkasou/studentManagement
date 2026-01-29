package com.prakashmalla.sms.service;

import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.payload.request.StatusChangeRequest;
import com.prakashmalla.sms.payload.request.StudentDataRequest;
import com.prakashmalla.sms.payload.request.StudentRequest;

public interface StudentService {

    GlobalResponse createStudent(StudentRequest request);

    GlobalResponse getStudentById(Long id);

    GlobalResponse getAllStudents();
    GlobalResponse findAllStudents(StudentDataRequest request);

    GlobalResponse updateStudent(Long id, StudentRequest request);

    GlobalResponse changeStudentStatus(Long id, StatusChangeRequest request);


}
