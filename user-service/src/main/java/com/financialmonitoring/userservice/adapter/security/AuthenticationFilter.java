package com.financialmonitoring.userservice.adapter.security;

import com.financialmonitoring.userservice.adapter.utils.JwtTokenUtils;
import com.financialmonitoring.userservice.domain.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final UserService authService;
    private final JwtTokenUtils jwtTokenUtils;

    public AuthenticationFilter(
            UserService authService,
            JwtTokenUtils jwtTokenUtils
    ) {
        this.authService = authService;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) {
        try {
            String token = jwtTokenUtils.getTokenFromRequest(request.getHeader(HttpHeaders.AUTHORIZATION));

            if (jwtTokenUtils.isTokenValid(token)) {
                String tokenSubject = jwtTokenUtils.getTokenSubject(token);

                UserDetails userDetails = authService.loadUserByUsername(tokenSubject);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("An error occurred in authentication filter: {}", e.getMessage());
        }
    }
}
