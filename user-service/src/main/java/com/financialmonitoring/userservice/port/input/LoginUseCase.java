package com.financialmonitoring.userservice.port.input;

import com.financialmonitoring.userservice.adapter.dto.LoginRequestDTO;
import com.financialmonitoring.userservice.adapter.dto.LoginResponseDTO;

public interface LoginUseCase {
    LoginResponseDTO login(LoginRequestDTO requestDTO);
}
