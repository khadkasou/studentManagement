package com.prakashmalla.sms.service.impl;

import com.prakashmalla.sms.core.enums.ApiStatusEnum;
import com.prakashmalla.sms.core.exception.GlobalException;
import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.core.payload.response.GlobalResponseBuilder;
import com.prakashmalla.sms.entity.UserEntity;
import com.prakashmalla.sms.enums.RoleEnum;
import com.prakashmalla.sms.payload.request.LoginRequest;
import com.prakashmalla.sms.payload.request.RegisterRequest;
import com.prakashmalla.sms.payload.response.LoginResponse;
import com.prakashmalla.sms.repository.UserRepository;
import com.prakashmalla.sms.service.AuthService;
import com.prakashmalla.sms.utils.JwtUtil;
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
            String email = authentication.getName();
            String role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(authority -> authority != null && !authority.isEmpty())
                    .findFirst()
                    .map(authority -> {
                        if (authority.startsWith("ROLE_")) {
                            return authority.substring(5);
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

            return GlobalResponseBuilder.buildSuccessResponseWithData("Login Success", loginResponse);

        } catch (BadCredentialsException e) {
            throw new GlobalException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public GlobalResponse register(RegisterRequest request) {
        // Validate email uniqueness
        if (userRepository.existsByEmail(request.getEmail())) {
         throw new GlobalException("Email already exists");
        }
        UserEntity user = UserEntity.builder()
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(RoleEnum.USER)
                .enabled(true)
                .build();
        userRepository.save(user);
        return GlobalResponseBuilder.buildSuccessResponse("Register Success");
    }
}
