package com.prakashmalla.sms.controller;

import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.payload.request.CourseRequest;
import com.prakashmalla.sms.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<GlobalResponse> createCourse(@Valid @RequestBody CourseRequest request) {
        GlobalResponse response = courseService.createCourse(request);
        return ResponseEntity.status(response.getHttpStatus())
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse> getCourseById(@PathVariable Long id) {
        GlobalResponse response = courseService.getCourseById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<GlobalResponse> getAllCourses() {
        GlobalResponse response = courseService.getAllCourses();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequest request) {
        GlobalResponse response = courseService.updateCourse(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse> deleteCourse(@PathVariable Long id) {
        GlobalResponse response = courseService.deleteCourse(id);
        return ResponseEntity.ok(response);
    }
}
