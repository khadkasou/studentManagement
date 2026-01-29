package com.prakashmalla.sms.controller;

import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.payload.request.CourseDataRequest;
import com.prakashmalla.sms.payload.request.CourseRequest;
import com.prakashmalla.sms.payload.request.StatusChangeRequest;
import com.prakashmalla.sms.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<GlobalResponse> createCourse(@Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok().body(courseService.createCourse(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping("/active-list")
    public ResponseEntity<GlobalResponse> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping("/list")
    public ResponseEntity<GlobalResponse> findAllCourses(CourseDataRequest request) {
        return ResponseEntity.ok(courseService.findAllCourses(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GlobalResponse> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.updateCourse(id, request));
    }

    @PatchMapping("/change-status/{id}")
    public ResponseEntity<GlobalResponse> changeStatus(@PathVariable Long id, @Valid @RequestBody StatusChangeRequest request) {
        return ResponseEntity.ok(courseService.changeCourseStatus(id, request));
    }
}
