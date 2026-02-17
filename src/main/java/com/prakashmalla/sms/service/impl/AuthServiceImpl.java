package com.prakashmalla.sms.service.impl;

import com.prakashmalla.sms.core.enums.ApiStatusEnum;
import com.prakashmalla.sms.core.exception.GlobalException;
import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.core.payload.response.GlobalResponseBuilder;
import com.prakashmalla.sms.entity.StudentEntity;
import com.prakashmalla.sms.entity.UserEntity;
import com.prakashmalla.sms.enums.RoleEnum;
import com.prakashmalla.sms.payload.request.LoginRequest;
import com.prakashmalla.sms.payload.request.RegisterRequest;
import com.prakashmalla.sms.payload.response.LoginResponse;
import com.prakashmalla.sms.payload.response.StudentResponse;
import com.prakashmalla.sms.payload.response.TokenResponse;
import com.prakashmalla.sms.repository.StudentRepository;
import com.prakashmalla.sms.repository.UserRepository;
import com.prakashmalla.sms.service.AuthService;
import com.prakashmalla.sms.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

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
    public GlobalResponse createUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
         throw new GlobalException("Email already exists");
        }
        UserEntity user = UserEntity.builder()
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(request.getRole())
                .enabled(true)
                .build();
        userRepository.save(user);
        return GlobalResponseBuilder.buildSuccessResponse("Create User Success");
    }

    @Override
    public GlobalResponse getLoggedInUser(String token) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String email = jwtUtil.extractUsername(token);
            String role= jwtUtil.getRoleFromToken(token);
            StudentEntity student = studentRepository.findByEmail(email);
            if (student == null) {
                UserEntity user= userRepository.findByEmail(email).orElseThrow(()-> new GlobalException("User not found"));
                TokenResponse response = modelMapper.map(user, TokenResponse.class);
                response.setRole(role);
                return GlobalResponseBuilder.buildSuccessResponseWithData("User information retrieved successfully", response);
            }
            StudentResponse response = modelMapper.map(student, StudentResponse.class);
            response.setRole(role);
            return GlobalResponseBuilder.buildSuccessResponseWithData("User information retrieved successfully", response);

        } catch (Exception e) {
            throw new GlobalException("Invalid or expired token", HttpStatus.UNAUTHORIZED);
        }
    }


}
