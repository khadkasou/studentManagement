package com.prakashmalla.sms.service.impl;

import com.prakashmalla.sms.core.enums.ApiStatusEnum;
import com.prakashmalla.sms.core.exception.GlobalException;
import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.entity.UserEntity;
import com.prakashmalla.sms.enums.RoleEnum;
import com.prakashmalla.sms.payload.request.LoginRequest;
import com.prakashmalla.sms.payload.request.RegisterRequest;
import com.prakashmalla.sms.payload.response.LoginResponse;
import com.prakashmalla.sms.repository.UserRepository;
import com.prakashmalla.sms.service.AuthService;
import com.prakashmalla.sms.utils.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public GlobalResponse login(LoginRequest request) throws GlobalException {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // Extract email and role from authentication
            String email = authentication.getName();
            String role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(authority -> authority != null && !authority.isEmpty())
                    .findFirst()
                    .map(authority -> {
                        if (authority.startsWith("ROLE_")) {
                            return authority.substring(5); // Remove "ROLE_" prefix
                        }
                        return authority;
                    })
                    .orElse("USER");

            // Generate token
            String token = jwtUtil.generateToken(email, role);

            // Build response
            LoginResponse loginResponse = LoginResponse.builder()
                    .token(token)
                    .email(email)
                    .role(role)
                    .type("Bearer")
                    .build();

            return GlobalResponse.builder()
                    .status(ApiStatusEnum.SUCCESS)
                    .httpStatus(HttpStatus.OK)
                    .message("Login successful")
                    .data(loginResponse)
                    .build();

        } catch (BadCredentialsException e) {
            throw new GlobalException("AUTH001", "Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public GlobalResponse register(RegisterRequest request) {
        // Validate email uniqueness
        if (userRepository.existsByEmail(request.getEmail())) {
            return GlobalResponse.builder()
                    .status(ApiStatusEnum.FAILED)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("Email already exists")
                    .build();
        }

        // Create user entity - always set role to USER for security
        // Admin role can only be created by system administrators, not through registration
        UserEntity user = UserEntity.builder()
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(RoleEnum.USER) // Force USER role - ignore any role from request
                .enabled(true)
                .build();

        // Save user
        user = userRepository.save(user);

        // Generate token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        // Build response
        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .type("Bearer")
                .build();

        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .httpStatus(HttpStatus.CREATED)
                .message("User registered successfully")
                .data(loginResponse)
                .build();
    }
}
