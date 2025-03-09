package com.financialmonitoring.userservice.core.utils;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtUtils {
    private static final String BEARER_PREFIX = "Bearer ";

    public String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        return extractTokenFromHeader(authHeader);
    }

    public String extractTokenFromHeader(String authHeader) {
        if (hasBearerToken(authHeader)) {
            return authHeader.replace(BEARER_PREFIX, "");
        }

        return null;
    }

    private boolean hasBearerToken(String authHeader) {
        return StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_PREFIX);
    }
}
