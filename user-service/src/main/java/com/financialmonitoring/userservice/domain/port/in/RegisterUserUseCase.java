package com.financialmonitoring.userservice.domain.port.in;

import com.financialmonitoring.userservice.adapter.dto.RegisterRequestDTO;
import com.financialmonitoring.userservice.adapter.dto.RegisterResponseDTO;

public interface RegisterUserUseCase {
    RegisterResponseDTO register(RegisterRequestDTO requestDTO);
}
