package com.financialmonitoring.userservice.core.service.impl;

import com.financialmonitoring.commonlib.utils.JwtUtils;
import com.financialmonitoring.userservice.config.exception.BadRequestException;
import com.financialmonitoring.userservice.core.dto.LoginRequestDTO;
import com.financialmonitoring.userservice.core.dto.LoginResponseDTO;
import com.financialmonitoring.userservice.core.dto.LoginResponseDTO.UserLoginResponseDTO;
import com.financialmonitoring.userservice.core.model.User;
import com.financialmonitoring.userservice.core.repository.UserRepository;
import com.financialmonitoring.userservice.core.security.TokenProvider;
import com.financialmonitoring.userservice.core.service.UserService;

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

    public UserServiceImpl(
            UserRepository userRepository,
            TokenProvider tokenProvider,
            @Lazy AuthenticationManager authenticationManager,
            @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
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
        return tokenProvider.validateToken(JwtUtils.extractTokenFromHeader(authHeader));
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        try {
            User user =
                    userRepository
                            .findByEmail(requestDTO.getEmail())
                            .orElseThrow(
                                    () ->
                                            new BadRequestException(
                                                    "User not found with the given email: "
                                                            + requestDTO.getEmail()));

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
}
