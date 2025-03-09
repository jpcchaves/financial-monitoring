package com.financialmonitoring.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthRouteLocator {

    @Bean
    public RouteLocator authRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(
                        "auth-routes",
                        r ->
                                r.path("/login", "/register")
                                        .filters(f -> f.prefixPath("/api/v1/auth"))
                                        .uri("lb://USER-SERVICE"))
                .build();
    }
}
