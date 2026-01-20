package com.prakashmalla.sms.service;

import com.prakashmalla.sms.core.exception.GlobalException;
import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.payload.request.LoginRequest;
import com.prakashmalla.sms.payload.request.RegisterRequest;

public interface AuthService {
    GlobalResponse login(LoginRequest request) throws GlobalException;
    GlobalResponse register(RegisterRequest request);
}
