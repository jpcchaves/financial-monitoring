package com.financialmonitoring.userservice.port.input;

import com.financialmonitoring.userservice.adapter.dto.RegisterRequestDTO;
import com.financialmonitoring.userservice.adapter.dto.RegisterResponseDTO;

public interface RegisterUserUseCase {
    RegisterResponseDTO register(RegisterRequestDTO requestDTO);
}
