package com.financialmonitoring.userservice.core.security;

import static com.financialmonitoring.userservice.core.security.constants.SecurityConstants.BCRYPT_SALT;
import static com.financialmonitoring.userservice.core.security.constants.SecurityConstants.PUBLIC_ROUTES;

import com.financialmonitoring.userservice.config.exception.InternalServerErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final AuthenticationFilter authenticationFilter;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfig(
            AuthenticationFilter authenticationFilter,
            AccessDeniedHandler accessDeniedHandler,
            AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationFilter = authenticationFilter;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_SALT);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            http.formLogin(AbstractHttpConfigurer::disable)
                    .httpBasic(AbstractHttpConfigurer::disable)
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(
                            session ->
                                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .headers(
                            headers ->
                                    headers.contentTypeOptions(Customizer.withDefaults())
                                            .xssProtection(Customizer.withDefaults())
                                            .cacheControl(Customizer.withDefaults())
                                            .httpStrictTransportSecurity(Customizer.withDefaults())
                                            .frameOptions(Customizer.withDefaults())
                                            .disable())
                    .exceptionHandling(
                            exceptionHandlingConfigurer ->
                                    exceptionHandlingConfigurer
                                            .accessDeniedHandler(accessDeniedHandler)
                                            .authenticationEntryPoint(authenticationEntryPoint))
                    .authorizeHttpRequests(
                            authorize ->
                                    authorize
                                            .requestMatchers(PUBLIC_ROUTES)
                                            .permitAll()
                                            .anyRequest()
                                            .authenticated())
                    .cors(Customizer.withDefaults());

            http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        } catch (Exception e) {
            logger.error("An error occurred on security config", e);
            throw new InternalServerErrorException(
                    "Ocorreu um erro inesperado processando sua requisição!");
        }
    }
}
