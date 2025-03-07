package com.financialmonitoring.userservice.core.service;

import com.financialmonitoring.userservice.core.dto.LoginRequestDTO;
import com.financialmonitoring.userservice.core.dto.LoginResponseDTO;
import com.financialmonitoring.userservice.core.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User getUserByEmail(String email);

    boolean verifyToken(String token);

    LoginResponseDTO login(LoginRequestDTO requestDTO);
}
