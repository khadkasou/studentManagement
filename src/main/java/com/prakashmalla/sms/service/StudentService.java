package com.prakashmalla.sms.service;

import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.payload.request.StudentRequest;

public interface StudentService {

    GlobalResponse createStudent(StudentRequest request);

    GlobalResponse getStudentById(Long id);

    GlobalResponse getAllStudents();

    GlobalResponse updateStudent(Long id, StudentRequest request);

    GlobalResponse deleteStudent(Long id);

    GlobalResponse getStudentsByStatus(String status);

}
