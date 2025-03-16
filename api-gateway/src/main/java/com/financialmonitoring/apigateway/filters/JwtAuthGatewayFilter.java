package com.financialmonitoring.apigateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.financialmonitoring.commonlib.dto.ExceptionResponseDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthGatewayFilter extends AbstractGatewayFilterFactory<JwtAuthGatewayFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthGatewayFilter.class);
    private static final ExceptionResponseDTO UNAUTHORIZED_EXCEPTION = ExceptionResponseDTO.builder()
            .withMessage("Unauthorized!")
            .withDetails("There was a problem authenticating the user.")
            .build();

    private final ObjectMapper objectMapper;
    private final WebClient.Builder webClientBuilder;

    public JwtAuthGatewayFilter(ObjectMapper objectMapper, WebClient.Builder webClientBuilder) {
        this.objectMapper = objectMapper;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (request.getURI().getPath().contains("/login")
                    || request.getURI().getPath().contains("/register")) {
                return chain.filter(exchange);
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (StringUtils.isBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

                return exchange.getResponse()
                        .writeWith(Mono.just(exchange.getResponse()
                                .bufferFactory()
                                .wrap(getUnauthorizedExceptionBytes())
                        ));
            }

            return webClientBuilder
                    .build()
                    .get()
                    .uri("lb://USER-SERVICE/api/v1/auth/verify-token")
                    .header("Authorization", authHeader)
                    .retrieve()
                    .toBodilessEntity()
                    .flatMap(response -> chain.filter(exchange))
                    .onErrorResume(
                            error -> {
                                logger.error("Failed to verify token: {}", error.getMessage(), error);
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

                                return exchange.getResponse()
                                        .writeWith(Mono.just(
                                                exchange.getResponse()
                                                        .bufferFactory()
                                                        .wrap(getUnauthorizedExceptionBytes())
                                        ));
                            });
        };
    }

    private byte[] getUnauthorizedExceptionBytes() {
        try {
            return objectMapper.writeValueAsBytes(UNAUTHORIZED_EXCEPTION);
        } catch (JsonProcessingException e) {
            //
        }

        return new byte[0];
    }

    public static class Config {

    }
}
