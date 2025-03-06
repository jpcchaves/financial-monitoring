package com.financialmonitoring.userservice.core.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.financialmonitoring.userservice.config.exception.BadRequestException;
import com.financialmonitoring.userservice.core.dto.LoginResponseDTO;
import com.financialmonitoring.userservice.core.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

@Component
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_CLAIM_KEY = "authorities";
    private static final String USER_CLAIM_KEY = "user";

    private final ObjectMapper objectMapper;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-time}")
    private String expirationTime;

    public TokenProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Instant issuedAtInstant = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        Instant expirationInstant =
                issuedAtInstant.plus(Long.parseLong(expirationTime), ChronoUnit.MILLIS);

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(Date.from(issuedAtInstant))
                .expiration(Date.from(expirationInstant))
                .signWith(generateKey())
                .claims(getClaims(user, authentication))
                .compact();
    }

    public boolean isTokenValid(String token) {
        return StringUtils.hasText(token) && this.validateToken(token);
    }

    public String getTokenSubject(String token) {
        Claims claims =
                Jwts.parser()
                        .verifyWith(generateKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

        return claims.getSubject();
    }

    public String getClaimFromTokenByKey(String token, String key) {
        try {
            Map<String, Object> claims =
                    Jwts.parser()
                            .verifyWith(generateKey())
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();

            Map<String, Object> userClaim =
                    objectMapper.convertValue(claims.get(USER_CLAIM_KEY), new TypeReference<>() {});

            return String.valueOf(userClaim.get(key));
        } catch (Exception e) {
            logger.error("Could not get all claims Token from passed token");
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(generateKey()).build().parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token: {}", ex.getMessage());
            throw new BadRequestException("Token inválido!");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token: {}", ex.getMessage());
            throw new BadRequestException("Token expirado!");
        } catch (UnsupportedJwtException ex) {
            logger.error("Invalid JWT format: {}", ex.getMessage());
            throw new BadRequestException("Formato de token inválido!");
        } catch (IllegalArgumentException | SecurityException ex) {
            logger.error("Unexpected error validating token: {}", ex.getMessage());
            throw new BadRequestException("Ocorreu um ero ao processar sua requisição!");
        }
    }

    private Map<String, ?> getClaims(User user, Authentication authentication) {
        return Map.of(
                USER_CLAIM_KEY,
                getUserClaimValue(user),
                AUTHORITIES_CLAIM_KEY,
                authentication.getAuthorities());
    }

    private LoginResponseDTO getUserClaimValue(User user) {
        return new LoginResponseDTO(user.getId(), user.getEmail());
    }

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}
