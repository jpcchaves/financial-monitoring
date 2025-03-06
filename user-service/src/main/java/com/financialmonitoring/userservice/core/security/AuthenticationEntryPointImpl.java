package com.financialmonitoring.userservice.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financialmonitoring.commonlib.dto.ExceptionResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    public AuthenticationEntryPointImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ExceptionResponseDTO exceptionResponseDTO =
                ExceptionResponseDTO.builder()
                        .withMessage("Não autorizado!")
                        .withDetails("Você precisa estar autenticado para acessar esse recurso!")
                        .build();

        response.getWriter().write(objectMapper.writer().writeValueAsString(exceptionResponseDTO));
    }
}
