package com.prakashmalla.sms.controller;

import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.payload.request.StudentRequest;
import com.prakashmalla.sms.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<GlobalResponse> createStudent(@Valid @RequestBody StudentRequest request) {
        GlobalResponse response = studentService.createStudent(request);
        return ResponseEntity.status(response.getHttpStatus())
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse> getStudentById(@PathVariable Long id) {
        GlobalResponse response = studentService.getStudentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<GlobalResponse> getAllStudents() {
        GlobalResponse response = studentService.getAllStudents();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {
        GlobalResponse response = studentService.updateStudent(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse> deleteStudent(@PathVariable Long id) {
        GlobalResponse response = studentService.deleteStudent(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<GlobalResponse> getStudentsByStatus(@PathVariable String status) {
        GlobalResponse response = studentService.getStudentsByStatus(status);
        return ResponseEntity.ok(response);
    }
}
