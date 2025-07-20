package com.financialmonitoring.userservice.domain.port.in;

import com.financialmonitoring.userservice.domain.dto.LoginRequestDTO;
import com.financialmonitoring.userservice.domain.dto.LoginResponseDTO;

public interface LoginUseCase {
    LoginResponseDTO login(LoginRequestDTO requestDTO);
}
