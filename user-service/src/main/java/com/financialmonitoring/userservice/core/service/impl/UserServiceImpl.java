package com.financialmonitoring.userservice.core.service.impl;

import com.financialmonitoring.userservice.config.exception.BadRequestException;
import com.financialmonitoring.userservice.core.model.User;
import com.financialmonitoring.userservice.core.repository.UserRepository;
import com.financialmonitoring.userservice.core.service.UserService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
