package com.financialmonitoring.userservice.domain.port.in;

import com.financialmonitoring.userservice.domain.dto.RegisterRequestDTO;
import com.financialmonitoring.userservice.domain.dto.RegisterResponseDTO;

public interface RegisterUserUseCase {
    RegisterResponseDTO register(RegisterRequestDTO requestDTO);
}
