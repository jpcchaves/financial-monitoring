package com.financialmonitoring.userservice.adapter.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financialmonitoring.commonlib.dto.ExceptionResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public AccessDeniedHandlerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ExceptionResponseDTO exceptionResponseDTO =
                new ExceptionResponseDTO(
                        "Acesso negado!", "Você não possui permissão para acessar esse recurso!");

        response.getWriter().write(objectMapper.writer().writeValueAsString(exceptionResponseDTO));
    }
}
