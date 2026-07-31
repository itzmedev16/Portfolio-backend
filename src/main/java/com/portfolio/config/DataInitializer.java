package com.portfolio.config;

import com.portfolio.entity.AdminUser;
import com.portfolio.repository.AdminUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String defaultEmail;

    @Value("${app.admin.password}")
    private String defaultPassword;

    public DataInitializer(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Check if default admin account exists
        if (!adminUserRepository.existsByEmail(defaultEmail)) {
            AdminUser admin = AdminUser.builder()
                    .email(defaultEmail)
                    .password(passwordEncoder.encode(defaultPassword))
                    .role("ROLE_ADMIN")
                    .build();

            adminUserRepository.save(admin);
            log.info("Successfully initialized default administrator account with email: {}", defaultEmail);
        } else {
            log.info("Default administrator account already exists. Skipping initialization.");
        }
    }
}
