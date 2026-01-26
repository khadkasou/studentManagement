package com.prakashmalla.sms.service;

import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.payload.request.CourseDataRequest;
import com.prakashmalla.sms.payload.request.CourseRequest;
import com.prakashmalla.sms.payload.request.StatusChangeRequest;

public interface CourseService {

    GlobalResponse createCourse(CourseRequest request);

    GlobalResponse getCourseById(Long id);

    GlobalResponse getAllCourses();
    GlobalResponse findAllCourses(CourseDataRequest request);

    GlobalResponse updateCourse(Long id, CourseRequest request);

    GlobalResponse changeCourseStatus(Long id, StatusChangeRequest request);
}
