package com.prakashmalla.sms.config;

import com.prakashmalla.sms.entity.UserEntity;
import com.prakashmalla.sms.enums.RoleEnum;
import com.prakashmalla.sms.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;
    private final Validator validator;

    @Override
    public void run(String... args) {
        loadAdminUser();
    }
    private void loadAdminUser() {
        // Check if admin user already exists
        if (userRepository.existsByRole(RoleEnum.ADMIN)) {
            log.info("Admin user already exists. Skipping admin user creation.");
            return;
        }

        // Validate admin email from configuration
        if (adminProperties.getEmail() == null || adminProperties.getEmail().trim().isEmpty()) {
            return;
        }

        // Validate email format
        UserEntity tempUser = UserEntity.builder()
                .email(adminProperties.getEmail())
                .password("temp")
                .role(RoleEnum.ADMIN)
                .enabled(true)
                .build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(tempUser);
        if (!violations.isEmpty()) {
            log.error("Invalid admin email format: {}", adminProperties.getEmail());
            violations.forEach(v -> log.error("Validation error: {}", v.getMessage()));
            return;
        }

        // Create admin user from configuration
        UserEntity adminUser = UserEntity.builder()
                .password(passwordEncoder.encode(adminProperties.getPassword()))
                .email(adminProperties.getEmail())
                .role(RoleEnum.ADMIN)
                .enabled(true)
                .build();

        userRepository.save(adminUser);
        log.info("Admin user loaded successfully from configuration!");
        log.info("Email: {}", adminProperties.getEmail());
    }
}
