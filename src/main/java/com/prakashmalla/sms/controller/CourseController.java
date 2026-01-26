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
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<GlobalResponse> createCourse(@Valid @RequestBody CourseRequest request) {
        GlobalResponse response = courseService.createCourse(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse> getCourseById(@PathVariable Long id) {
        GlobalResponse response = courseService.getCourseById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active-list")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse> getAllCourses() {
        GlobalResponse response = courseService.getAllCourses();
        return ResponseEntity.ok(response);
    }
    @PostMapping("/list")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse> findAllCourses(CourseDataRequest request) {
        GlobalResponse response = courseService.findAllCourses(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<GlobalResponse> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        GlobalResponse response = courseService.updateCourse(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/change-status/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse> changeStatus(@PathVariable Long id, @Valid @RequestBody StatusChangeRequest request) {
        GlobalResponse response = courseService.changeCourseStatus(id, request);
        return ResponseEntity.ok(response);
    }
}
