package com.prakashmalla.sms.service.impl;

import com.prakashmalla.sms.core.enums.ApiStatusEnum;
import com.prakashmalla.sms.core.enums.StatusEnum;
import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.entity.CourseEntity;
import com.prakashmalla.sms.mapper.CourseMapper;
import com.prakashmalla.sms.payload.request.CourseRequest;
import com.prakashmalla.sms.payload.response.CourseResponse;
import com.prakashmalla.sms.repository.CourseRepository;
import com.prakashmalla.sms.service.CourseService;
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
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public GlobalResponse createCourse(CourseRequest request) {
        CourseEntity course = courseMapper.toEntity(request);
        if (course.getStatus() == null) {
            course.setStatus(StatusEnum.ACTIVE);
        }
        courseRepository.save(course);
        return GlobalResponse.builder().status(ApiStatusEnum.SUCCESS)
                .httpStatus(HttpStatus.CREATED).message("Course added successfully").build();
    }

    @Override
    @Transactional(readOnly = true)
    public GlobalResponse getCourseById(Long id) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
        CourseResponse response = courseMapper.toResponse(course);
        return GlobalResponse.builder().status(ApiStatusEnum.SUCCESS).message("Course retrieved successfully")
                .data(response)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public GlobalResponse getAllCourses() {
        List<CourseResponse> responses = courseRepository.findAll().stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .message("Courses retrieved successfully")
                .data(responses)
                .build();
    }

    @Override
    public GlobalResponse updateCourse(Long id, CourseRequest request) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
        
        course.setName(request.getName());
        if (request.getStatus() != null) {
            course.setStatus(request.getStatus());
        }
        
        course = courseRepository.save(course);
        CourseResponse response = courseMapper.toResponse(course);
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .message("Course updated successfully")
                .data(response)
                .build();
    }

    @Override
    public GlobalResponse deleteCourse(Long id) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
        courseRepository.delete(course);
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .message("Course deleted successfully")
                .build();
    }
}
