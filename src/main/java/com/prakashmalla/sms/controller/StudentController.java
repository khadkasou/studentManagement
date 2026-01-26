package com.prakashmalla.sms.controller;

import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.payload.request.StatusChangeRequest;
import com.prakashmalla.sms.payload.request.StudentDataRequest;
import com.prakashmalla.sms.payload.request.StudentRequest;
import com.prakashmalla.sms.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<GlobalResponse> createStudent(@Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok().body(studentService.createStudent(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/active-list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse> findAllStudents(@Valid @RequestBody StudentDataRequest request) {
        return ResponseEntity.ok(studentService.findAllStudents(request));
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<GlobalResponse> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(studentService.updateStudent(id, request));
    }

    @DeleteMapping("/change-status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse> deleteStudent(@PathVariable Long id, @RequestBody StatusChangeRequest request) {
        return ResponseEntity.ok( studentService.changeStudentStatus(id,request));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse> getStudentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(studentService.getStudentsByStatus(status));
    }

}
