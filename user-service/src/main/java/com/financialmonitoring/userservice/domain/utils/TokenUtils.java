package com.financialmonitoring.userservice.domain.utils;

import org.springframework.security.core.Authentication;

public interface TokenUtils {

    String generateToken(Authentication authentication);

    boolean isTokenValid(String token);

    String getTokenSubject(String token);

    boolean validateToken(String token);

    String getTokenFromRequest(String authHeader);

    String extractTokenFromHeader(String authHeader);
}
