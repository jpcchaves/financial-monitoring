package com.financialmonitoring.userservice.config.auth;

import com.financialmonitoring.userservice.adapter.utils.JwtUtils;
import com.financialmonitoring.userservice.adapter.utils.TokenUtils;
import com.financialmonitoring.userservice.domain.port.out.AuthRepositoryPort;
import com.financialmonitoring.userservice.domain.port.out.RoleRepositoryPort;
import com.financialmonitoring.userservice.domain.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthServiceConfig {

    @Bean
    public AuthService authService(AuthRepositoryPort authRepositoryPort, RoleRepositoryPort roleRepositoryPort,
                                   @Lazy PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager,
                                   TokenUtils tokenUtils, JwtUtils jwtUtils) {
        return new AuthService(authRepositoryPort, roleRepositoryPort, passwordEncoder, authenticationManager,
                tokenUtils, jwtUtils);
    }
}
