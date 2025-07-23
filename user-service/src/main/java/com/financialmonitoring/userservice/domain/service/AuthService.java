package com.financialmonitoring.userservice.domain.service;

import com.financialmonitoring.userservice.adapter.dto.*;
import com.financialmonitoring.userservice.adapter.out.entity.Role;
import com.financialmonitoring.userservice.adapter.out.entity.User;
import com.financialmonitoring.userservice.adapter.utils.JwtUtils;
import com.financialmonitoring.userservice.adapter.utils.TokenUtils;
import com.financialmonitoring.userservice.config.exception.BadRequestException;
import com.financialmonitoring.userservice.domain.port.factory.UserFactory;
import com.financialmonitoring.userservice.domain.port.in.GetUserByEmailUseCase;
import com.financialmonitoring.userservice.domain.port.in.LoginUseCase;
import com.financialmonitoring.userservice.domain.port.in.RegisterUserUseCase;
import com.financialmonitoring.userservice.domain.port.in.VerifyTokenUseCase;
import com.financialmonitoring.userservice.domain.port.out.AuthRepositoryPort;
import com.financialmonitoring.userservice.domain.port.out.RoleRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthService implements LoginUseCase, RegisterUserUseCase, VerifyTokenUseCase, GetUserByEmailUseCase {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AuthRepositoryPort authRepositoryPort;
    private final RoleRepositoryPort roleRepositoryPort;
    private final UserFactory userFactory;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final JwtUtils jwtUtils;

    public AuthService(
            AuthRepositoryPort authRepositoryPort,
            RoleRepositoryPort roleRepositoryPort,
            UserFactory userFactory,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            TokenUtils tokenUtils,
            JwtUtils jwtUtils
    ) {
        this.authRepositoryPort = authRepositoryPort;
        this.roleRepositoryPort = roleRepositoryPort;
        this.userFactory = userFactory;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        try {
            User user = getUserByEmail(requestDTO.getEmail());

            if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
                throw new BadRequestException("Invalid password");
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    requestDTO.getEmail(), requestDTO.getPassword());

            Authentication authentication = authenticationManager.authenticate(authToken);

            return new LoginResponseDTO(tokenUtils.generateToken(authentication),
                    new UserLoginResponseDTO(user.getId(), user.getEmail()));
        } catch (AuthenticationException e) {
            logger.error("Error authenticating user", e);
            throw new BadRequestException("Error authenticating user: " + e.getMessage());
        }
    }

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO requestDTO) {
        validateRegister(requestDTO);
        User user = userFactory.createUserFromDto(requestDTO, getDefaultUserRoles());
        user = authRepositoryPort.save(user);
        return new RegisterResponseDTO(user.getId(), user.getFirstName(), user.getEmail());
    }

    @Override
    public boolean verifyToken(String authHeader) {
        return tokenUtils.validateToken(jwtUtils.extractTokenFromHeader(authHeader));
    }

    @Override
    public User getUserByEmail(String email) {
        return authRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found with the given email: " + email));
    }

    private void validateRegister(RegisterRequestDTO requestDTO) {
        if (authRepositoryPort.existsByEmail(requestDTO.getEmail())) {
            throw new BadRequestException("Email already in use");
        }

        if (!requestDTO.getPassword()
                .equals(requestDTO.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }
    }

    private Set<Role> getDefaultUserRoles() {
        return Stream.of(roleRepositoryPort.findByName("ROLE_USER")
                        .orElseGet(() -> roleRepositoryPort.saveAndFlush(new Role("ROLE_USER"))))
                .collect(Collectors.toSet());
    }
}
