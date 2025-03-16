package com.financialmonitoring.apigateway.config;

import com.financialmonitoring.apigateway.filters.JwtAuthGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthRouteLocator {

    private final JwtAuthGatewayFilter jwtAuthGatewayFilter;

    public AuthRouteLocator(JwtAuthGatewayFilter jwtAuthGatewayFilter) {
        this.jwtAuthGatewayFilter = jwtAuthGatewayFilter;
    }

    @Bean
    public RouteLocator applicationRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(
                        "auth-routes",
                        r ->
                                r.path("/login", "/register")
                                        .filters(f -> f.prefixPath("/api/v1/auth"))
                                        .uri("lb://USER-SERVICE"))
                .route(
                        "transaction-service",
                        r ->
                                r.path("/transactions")
                                        .filters(f -> f.prefixPath("/api/v1")
                                                .filter(jwtAuthGatewayFilter.apply(new JwtAuthGatewayFilter.Config())))
                                        .uri("lb://TRANSACTION-SERVICE"))
                .build();
    }
}
