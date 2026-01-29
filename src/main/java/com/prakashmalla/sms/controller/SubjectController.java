package com.prakashmalla.sms.controller;

import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.payload.request.StatusChangeRequest;
import com.prakashmalla.sms.payload.request.SubjectDataRequest;
import com.prakashmalla.sms.payload.request.SubjectRequest;
import com.prakashmalla.sms.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("/create")
    public ResponseEntity<GlobalResponse> createSubject(@Valid @RequestBody SubjectRequest request) {
        return ResponseEntity.ok(subjectService.createSubject(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse> getSubjectById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @GetMapping("/active-list")
    public ResponseEntity<GlobalResponse> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubject());
    }

    @PostMapping("/list")
    public ResponseEntity<GlobalResponse> findAllSubjects(@RequestBody SubjectDataRequest request) {
        return ResponseEntity.ok( subjectService.findAllSubject(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GlobalResponse> updateSubject(@PathVariable Long id, @Valid @RequestBody SubjectRequest request) {
        return ResponseEntity.ok(subjectService.updateSubject(id, request));
    }

    @PatchMapping("/change-status/{id}")
    public ResponseEntity<GlobalResponse> changeStatus(@PathVariable Long id, @Valid @RequestBody StatusChangeRequest request) {
        return ResponseEntity.ok(subjectService.changeSubjectStatus(id, request));
    }
}
