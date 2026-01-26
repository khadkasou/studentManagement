package com.prakashmalla.sms.service.impl;

import com.prakashmalla.sms.core.enums.StatusEnum;
import com.prakashmalla.sms.core.exception.GlobalException;
import com.prakashmalla.sms.core.payload.response.DataPaginationResponse;
import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.core.payload.response.GlobalResponseBuilder;
import com.prakashmalla.sms.core.util.Helper;
import com.prakashmalla.sms.entity.CourseEntity;
import com.prakashmalla.sms.payload.request.CourseDataRequest;
import com.prakashmalla.sms.payload.request.CourseRequest;
import com.prakashmalla.sms.payload.request.StatusChangeRequest;
import com.prakashmalla.sms.payload.response.CourseResponse;
import com.prakashmalla.sms.repository.CourseRepository;
import com.prakashmalla.sms.service.CourseService;
import com.prakashmalla.sms.service.specification.CourseSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    public GlobalResponse createCourse(CourseRequest request) {
        CourseEntity course = courseRepository.findByShortName(request.getShortName());
        if (course != null) {
            throw new GlobalException("Course already exists");
        }
        course = CourseEntity.builder()
                .name(request.getName())
                .shortName(request.getShortName())
                .description(request.getDescription())
                .build();
        course.setStatus(StatusEnum.ACTIVE);
        courseRepository.save(course);
        return GlobalResponseBuilder.buildSuccessResponse("Course Created");
    }

    @Override
    public GlobalResponse getCourseById(Long id) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new GlobalException("Course not found"));
        CourseResponse response = modelMapper.map(course, CourseResponse.class);
        return GlobalResponseBuilder.buildSuccessResponseWithData("Course Found", response);
    }

    @Override
    public GlobalResponse getAllCourses() {
        List<CourseEntity> responses = courseRepository.findAllByStatusIs(StatusEnum.ACTIVE);
        List<CourseResponse> courseResponses = responses.stream().map(m -> modelMapper.map(m, CourseResponse.class)).toList();
        return GlobalResponseBuilder.buildSuccessResponseWithData("All Course Found", courseResponses);
    }

    @Override
    public GlobalResponse findAllCourses(CourseDataRequest request) {
        Specification<CourseEntity> specification = CourseSpecification.courseFilter(request);
        Page<CourseEntity> page = courseRepository.findAll(specification, Helper.getPageable(request));
        List<CourseResponse> courseResponses = page.getContent().stream()
                .map(m -> modelMapper.map(m, CourseResponse.class)).toList();
        DataPaginationResponse response = new DataPaginationResponse(page.getTotalElements(), courseResponses);
        return GlobalResponseBuilder.buildSuccessResponseWithData("All Course Found", response);
    }

    @Override
    public GlobalResponse updateCourse(Long id, CourseRequest request) {
        CourseEntity course = courseRepository.findById(id).orElseThrow(() -> new GlobalException("Course not found"));
        course.setName(request.getName());
        course.setShortName(request.getShortName());
        course.setDescription(request.getDescription());
        courseRepository.save(course);
        return GlobalResponseBuilder.buildSuccessResponse("Course Updated");
    }

    @Override
    public GlobalResponse changeCourseStatus(Long id, StatusChangeRequest request) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new GlobalException("Course not found"));
        course.setStatus(request.getStatus());
        courseRepository.save(course);
        return GlobalResponseBuilder.buildSuccessResponse("Status changed successfully");
    }
}
