package com.prakashmalla.sms.service.impl;

import com.prakashmalla.sms.core.enums.StatusEnum;
import com.prakashmalla.sms.core.exception.GlobalException;
import com.prakashmalla.sms.core.payload.response.DataPaginationResponse;
import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.core.payload.response.GlobalResponseBuilder;
import com.prakashmalla.sms.core.util.Helper;
import com.prakashmalla.sms.entity.AddressEntity;
import com.prakashmalla.sms.entity.CourseEntity;
import com.prakashmalla.sms.entity.StudentEntity;
import com.prakashmalla.sms.mapper.StudentMapper;
import com.prakashmalla.sms.payload.request.StatusChangeRequest;
import com.prakashmalla.sms.payload.request.StudentDataRequest;
import com.prakashmalla.sms.payload.request.StudentRequest;
import com.prakashmalla.sms.payload.response.AddressResponse;
import com.prakashmalla.sms.payload.response.CourseResponse;
import com.prakashmalla.sms.payload.response.StudentResponse;
import com.prakashmalla.sms.payload.response.SubjectResponse;
import com.prakashmalla.sms.repository.CourseRepository;
import com.prakashmalla.sms.repository.StudentRepository;
import com.prakashmalla.sms.service.StudentService;
import com.prakashmalla.sms.service.specification.StudentSpecification;
import com.prakashmalla.sms.utils.StudentCodeGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final StudentCodeGenerator studentCodeGenerator;

    @Override
    public GlobalResponse createStudent(StudentRequest request) {
        StudentEntity student = studentRepository.findByCitizenshipNumber(request.getCitizenshipNumber());
        if (student != null) {
            throw new GlobalException("Student already exists!");
        }
        student = modelMapper.map(request, StudentEntity.class);
        student.setStatus(StatusEnum.ACTIVE);

        if (request.getTemporaryAddress() != null) {
            AddressEntity temporaryAddress = modelMapper.map(request.getTemporaryAddress(), AddressEntity.class);
            student.setTemporaryAddress(temporaryAddress);
        }
        if (request.getPermanentAddress() != null) {
            AddressEntity permanentAddress = modelMapper.map(request.getPermanentAddress(), AddressEntity.class);
            student.setPermanentAddress(permanentAddress);
        }
        CourseEntity course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new GlobalException("Course not found!"));
        student.setCourse(course);
        student.setStudentCode(studentCodeGenerator.generateStudentCode(course.getShortName()));
        studentRepository.save(student);
        return GlobalResponseBuilder.buildSuccessResponse("Student Created");
    }

    @Override
    public GlobalResponse getStudentById(Long id) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        StudentResponse response = modelMapper.map(student, StudentResponse.class);

        if (student.getTemporaryAddress() != null) {
            response.setTemporaryAddress(modelMapper.map(student.getTemporaryAddress(), AddressResponse.class));
        }
        if (student.getPermanentAddress() != null) {
            response.setPermanentAddress(modelMapper.map(student.getPermanentAddress(), AddressResponse.class));
        }
        if (student.getCourse() != null) {
            CourseResponse courseResponse = modelMapper.map(student.getCourse(), CourseResponse.class);

            if (student.getCourse().getSubject() != null && !student.getCourse().getSubject().isEmpty()) {
                List<SubjectResponse> subjectResponses = student.getCourse().getSubject().stream()
                        .map(subject -> modelMapper.map(subject, SubjectResponse.class))
                        .collect(Collectors.toList());
                courseResponse.setSubjects(subjectResponses);
            }

            response.setCourse(courseResponse);
        }

        return GlobalResponseBuilder.buildSuccessResponseWithData("Student fetched successfully.", response);
    }

    @Override
    public GlobalResponse getAllStudents() {
        List<StudentEntity> students = studentRepository.findAllByStatusIs(StatusEnum.ACTIVE);
        List<StudentResponse> responses = students.stream()
                .map(student -> modelMapper.map(student, StudentResponse.class))
                .collect(Collectors.toList());
        return GlobalResponseBuilder.buildSuccessResponseWithData("Student list fetch success", responses);
    }

    @Override
    public GlobalResponse findAllStudents(StudentDataRequest request) {
        Specification<StudentEntity> specification = StudentSpecification.studentFilter(request);
        Page<StudentEntity> page = studentRepository.findAll(specification, Helper.getPageable(request));
        List<StudentResponse> responses = page.getContent().stream().map(m -> {
            StudentResponse response = modelMapper.map(m, StudentResponse.class);
            if (m.getCourse() != null) {
                CourseResponse courseResponse = modelMapper.map(m.getCourse(), CourseResponse.class);
                response.setCourse(courseResponse);
            }
            return response;
        }).toList();
        DataPaginationResponse response = new DataPaginationResponse(page.getTotalElements(), responses);
        return GlobalResponseBuilder.buildSuccessResponseWithData("Student list fetch success", response);
    }

    @Override
    public GlobalResponse updateStudent(Long id, StudentRequest request) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new GlobalException("Student not found with id: %s " + id));
        student = StudentMapper.INSTANCE.toUpdate(request, student);
        if (request.getTemporaryAddress() != null) {
            if (student.getTemporaryAddress() == null) {
                student.setTemporaryAddress(modelMapper.map(request.getTemporaryAddress(), AddressEntity.class));
            } else {
                modelMapper.map(request.getTemporaryAddress(), student.getTemporaryAddress());
            }
        }
        if (request.getPermanentAddress() != null) {
            if (student.getPermanentAddress() == null) {
                student.setPermanentAddress(modelMapper.map(request.getPermanentAddress(), AddressEntity.class));
            } else {
                modelMapper.map(request.getPermanentAddress(), student.getPermanentAddress());
            }
        }
        CourseEntity course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new GlobalException("Course not found!"));
        student.setCourse(course);

        studentRepository.save(student);
        return GlobalResponseBuilder.buildSuccessResponse("Student updated success.");
    }

    @Override
    public GlobalResponse changeStudentStatus(Long id, StatusChangeRequest request) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
        student.setStatus(request.getStatus());
        studentRepository.save(student);
        return GlobalResponseBuilder.buildSuccessResponse("Student deleted success.");
    }


}
