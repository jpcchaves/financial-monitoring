package com.financialmonitoring.userservice.core.service.impl;

import com.financialmonitoring.userservice.config.exception.BadRequestException;
import com.financialmonitoring.userservice.core.dto.LoginRequestDTO;
import com.financialmonitoring.userservice.core.dto.LoginResponseDTO;
import com.financialmonitoring.userservice.core.dto.LoginResponseDTO.UserLoginResponseDTO;
import com.financialmonitoring.userservice.core.dto.RegisterRequestDTO;
import com.financialmonitoring.userservice.core.dto.RegisterResponseDTO;
import com.financialmonitoring.userservice.core.model.Role;
import com.financialmonitoring.userservice.core.model.User;
import com.financialmonitoring.userservice.core.repository.RoleRepository;
import com.financialmonitoring.userservice.core.repository.UserRepository;
import com.financialmonitoring.userservice.core.security.TokenProvider;
import com.financialmonitoring.userservice.core.service.UserService;
import com.financialmonitoring.userservice.core.utils.JwtUtils;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            TokenProvider tokenProvider,
            @Lazy AuthenticationManager authenticationManager,
            @Lazy PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () ->
                                new BadRequestException(
                                        "User not found with the given email: " + email));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getUserByEmail(email);
    }

    @Override
    public boolean verifyToken(String authHeader) {
        return tokenProvider.validateToken(jwtUtils.extractTokenFromHeader(authHeader));
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        try {
            User user = getUserByEmail(requestDTO.getEmail());

            if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
                throw new BadRequestException("Invalid password");
            }

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            requestDTO.getEmail(), requestDTO.getPassword());

            Authentication authentication = authenticationManager.authenticate(authToken);

            return new LoginResponseDTO(
                    tokenProvider.generateToken(authentication),
                    new UserLoginResponseDTO(user.getId(), user.getEmail()));

        } catch (AuthenticationException e) {
            logger.error("Error authenticating user", e);
            throw new BadRequestException("Error authenticating user: " + e.getMessage());
        }
    }

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO requestDTO) {
        validateRegister(requestDTO);

        User user = User.builder()
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .firstName(requestDTO.getFirstName())
                .lastName(requestDTO.getLastName())
                .roles(Set.of(getDefaultUserRoles()))
                .active(Boolean.TRUE)
                .build();

        user = userRepository.save(user);

        return new RegisterResponseDTO(user.getId(), user.getFirstName(), user.getEmail());
    }

    private void validateRegister(RegisterRequestDTO requestDTO) {
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new BadRequestException("Email already in use");
        }

        if (!requestDTO.getPassword().equals(requestDTO.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }
    }

    private Role getDefaultUserRoles() {
        return roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.saveAndFlush(new Role("ROLE_USER")));
    }
}
