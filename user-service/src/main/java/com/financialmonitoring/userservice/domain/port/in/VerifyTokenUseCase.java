package com.financialmonitoring.userservice.domain.port.in;

public interface VerifyTokenUseCase {
    boolean verifyToken(String authHeader);
}
