package com.prakashmalla.sms.service;

import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.payload.request.CourseRequest;

public interface CourseService {

    GlobalResponse createCourse(CourseRequest request);

    GlobalResponse getCourseById(Long id);

    GlobalResponse getAllCourses();

    GlobalResponse updateCourse(Long id, CourseRequest request);

    GlobalResponse deleteCourse(Long id);
}
