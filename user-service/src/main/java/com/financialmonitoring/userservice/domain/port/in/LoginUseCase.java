package com.financialmonitoring.userservice.domain.port.in;

import com.financialmonitoring.userservice.adapter.dto.LoginRequestDTO;
import com.financialmonitoring.userservice.adapter.dto.LoginResponseDTO;

public interface LoginUseCase {
    LoginResponseDTO login(LoginRequestDTO requestDTO);
}
