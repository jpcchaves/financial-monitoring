package com.financialmonitoring.userservice.core.security;

import com.financialmonitoring.userservice.core.utils.JwtUtils;
import com.financialmonitoring.userservice.core.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AuthenticationFilter(
            TokenProvider tokenProvider, UserService userService, JwtUtils jwtUtils) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String token = jwtUtils.getTokenFromRequest(request);

            if (tokenProvider.isTokenValid(token)) {
                String tokenSubject = tokenProvider.getTokenSubject(token);

                UserDetails userDetails = userService.loadUserByUsername(tokenSubject);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                userDetails.getPassword(),
                                userDetails.getAuthorities());

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("An error occurred in authentication filter: {}", e.getMessage());
        }
    }
}
