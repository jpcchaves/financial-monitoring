package com.financialmonitoring.commonlib.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

public class JwtUtils {
    private static final String BEARER_PREFIX = "Bearer ";

    public static String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (hasBearerToken(authHeader)) {
            return authHeader.replace(BEARER_PREFIX, "");
        }

        return null;
    }

    private static boolean hasBearerToken(String authHeader) {
        return StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_PREFIX);
    }
}
