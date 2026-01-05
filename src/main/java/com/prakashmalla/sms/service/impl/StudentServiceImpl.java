package com.prakashmalla.sms.service.impl;

import com.prakashmalla.sms.core.enums.ApiStatusEnum;
import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.entity.CourseEntity;
import com.prakashmalla.sms.entity.StudentEntity;
import com.prakashmalla.sms.enums.StatusEnum;
import com.prakashmalla.sms.mapper.AddressMapper;
import com.prakashmalla.sms.mapper.CourseMapper;
import com.prakashmalla.sms.mapper.StudentMapper;
import com.prakashmalla.sms.payload.request.StudentRequest;
import com.prakashmalla.sms.payload.response.StudentResponse;
import com.prakashmalla.sms.repository.CourseRepository;
import com.prakashmalla.sms.repository.StudentRepository;
import com.prakashmalla.sms.service.StudentService;
import com.prakashmalla.sms.utils.StudentCodeGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;
    private final AddressMapper addressMapper;
    private final CourseMapper courseMapper;

    @Override
    public GlobalResponse createStudent(StudentRequest request) {
        StudentEntity student = studentMapper.toEntity(request);

        // Set default status if not provided
        if (student.getStatus() == null) {
            student.setStatus(StatusEnum.ACTIVE);
        }

        // Map addresses
        if (request.getTemporaryAddress() != null) {
            student.setTemporaryAddress(addressMapper.toEntity(request.getTemporaryAddress()));
        }
        if (request.getPermanentAddress() != null) {
            student.setPermanentAddress(addressMapper.toEntity(request.getPermanentAddress()));
        }

        // Save student first to get ID
        student = studentRepository.save(student);

        // Generate and set student code
        student.setStudentCode(StudentCodeGenerator.generateStudentCode(student.getId()));
        student = studentRepository.save(student);

        // Handle courses
        if (request.getCourseIds() != null && !request.getCourseIds().isEmpty()) {
            List<CourseEntity> courses = courseRepository.findAllById(request.getCourseIds());
            student.setCourses(courses);
            student = studentRepository.save(student);
        }

        StudentResponse response = studentMapper.toResponse(student);
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .httpStatus(HttpStatus.CREATED)
                .message("Student created successfully")
                .data(response)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public GlobalResponse getStudentById(Long id) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
        StudentResponse response = studentMapper.toResponse(student);
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .message("Student retrieved successfully")
                .data(response)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public GlobalResponse getAllStudents() {
        List<StudentResponse> responses = studentRepository.findAll().stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .message("Students retrieved successfully")
                .data(responses)
                .build();
    }

    @Override
    public GlobalResponse updateStudent(Long id, StudentRequest request) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        // Update basic fields
        student.setFirstName(request.getFirstName());
        student.setMiddleName(request.getMiddleName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setNationality(request.getNationality());
        student.setCitizenshipNumber(request.getCitizenshipNumber());
        student.setGuardianName(request.getGuardianName());
        student.setGuardianContactNumber(request.getGuardianContactNumber());
        student.setEnrollmentDate(request.getEnrollmentDate());
        student.setGender(request.getGender());
        if (request.getStatus() != null) {
            student.setStatus(request.getStatus());
        }

        // Update addresses
        if (request.getTemporaryAddress() != null) {
            if (student.getTemporaryAddress() != null) {
                student.getTemporaryAddress().setCity(request.getTemporaryAddress().getCity());
                student.getTemporaryAddress().setStreet(request.getTemporaryAddress().getStreet());
                student.getTemporaryAddress().setState(request.getTemporaryAddress().getState());
                student.getTemporaryAddress().setZip(request.getTemporaryAddress().getZip());
            } else {
                student.setTemporaryAddress(addressMapper.toEntity(request.getTemporaryAddress()));
            }
        }

        if (request.getPermanentAddress() != null) {
            if (student.getPermanentAddress() != null) {
                student.getPermanentAddress().setCity(request.getPermanentAddress().getCity());
                student.getPermanentAddress().setStreet(request.getPermanentAddress().getStreet());
                student.getPermanentAddress().setState(request.getPermanentAddress().getState());
                student.getPermanentAddress().setZip(request.getPermanentAddress().getZip());
            } else {
                student.setPermanentAddress(addressMapper.toEntity(request.getPermanentAddress()));
            }
        }

        // Update courses
        if (request.getCourseIds() != null) {
            List<CourseEntity> courses = courseRepository.findAllById(request.getCourseIds());
            student.setCourses(courses);
        }

        student = studentRepository.save(student);
        StudentResponse response = studentMapper.toResponse(student);
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .message("Student updated successfully")
                .data(response)
                .build();
    }

    @Override
    public GlobalResponse deleteStudent(Long id) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
        studentRepository.delete(student);
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .message("Student deleted successfully")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public GlobalResponse getStudentsByStatus(String status) {
        StatusEnum statusEnum = StatusEnum.valueOf(status.toUpperCase());
        List<StudentResponse> responses = studentRepository.findAll().stream()
                .filter(student -> student.getStatus() == statusEnum)
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .message("Students retrieved successfully by status")
                .data(responses)
                .build();
    }
}
