package com.prakashmalla.sms.controller;

import com.prakashmalla.sms.core.exception.GlobalException;
import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.payload.request.LoginRequest;
import com.prakashmalla.sms.payload.request.RegisterRequest;
import com.prakashmalla.sms.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse> login(@Valid @RequestBody LoginRequest request) throws GlobalException {
        GlobalResponse response = authService.login(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<GlobalResponse> register(@Valid @RequestBody RegisterRequest request) {
        GlobalResponse response = authService.register(request);
        return ResponseEntity.ok().body(response);
    }
}
