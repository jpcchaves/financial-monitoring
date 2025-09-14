package com.financialmonitoring.userservice.port.input;

public interface VerifyTokenUseCase {
    boolean verifyToken(String authHeader);
}
