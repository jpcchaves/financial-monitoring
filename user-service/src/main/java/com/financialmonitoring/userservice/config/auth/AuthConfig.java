package com.financialmonitoring.userservice.config.auth;

import com.financialmonitoring.userservice.adapter.utils.JwtUtils;
import com.financialmonitoring.userservice.adapter.utils.TokenUtils;
import com.financialmonitoring.userservice.domain.port.factory.UserFactory;
import com.financialmonitoring.userservice.domain.port.out.AuthRepositoryPort;
import com.financialmonitoring.userservice.domain.port.out.RoleRepositoryPort;
import com.financialmonitoring.userservice.domain.service.AuthService;
import com.financialmonitoring.userservice.domain.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {

    @Bean
    public AuthService authService(
            AuthRepositoryPort authRepositoryPort,
            RoleRepositoryPort roleRepositoryPort,
            PasswordEncoder passwordEncoder,
            UserFactory userFactory,
            AuthenticationManager authenticationManager,
            TokenUtils tokenUtils,
            JwtUtils jwtUtils
    ) {
        return new AuthService(authRepositoryPort, roleRepositoryPort, userFactory, passwordEncoder,
                authenticationManager, tokenUtils, jwtUtils);
    }

    @Bean
    public UserService userService(AuthRepositoryPort authRepositoryPort) {
        return new UserService(authRepositoryPort);
    }

}
