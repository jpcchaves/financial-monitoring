package com.financialmonitoring.userservice.core.service.impl;

import com.financialmonitoring.commonlib.utils.JwtUtils;
import com.financialmonitoring.userservice.config.exception.BadRequestException;
import com.financialmonitoring.userservice.core.model.User;
import com.financialmonitoring.userservice.core.repository.UserRepository;
import com.financialmonitoring.userservice.core.security.TokenProvider;
import com.financialmonitoring.userservice.core.service.UserService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public UserServiceImpl(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
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
}
