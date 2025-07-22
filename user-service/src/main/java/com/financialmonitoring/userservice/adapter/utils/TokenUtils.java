package com.financialmonitoring.userservice.adapter.utils;

import com.financialmonitoring.userservice.adapter.dto.UserLoginResponseDTO;
import com.financialmonitoring.userservice.adapter.out.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Component
public class TokenUtils {
    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    private static final String AUTHORITIES_CLAIM_KEY = "authorities";
    private static final String USER_CLAIM_KEY = "user";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-time}")
    private String expirationTime;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Instant issuedAtInstant = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        Instant expirationInstant = issuedAtInstant.plus(Long.parseLong(expirationTime), ChronoUnit.MILLIS);

        return Jwts.builder().subject(user.getEmail()).issuedAt(Date.from(issuedAtInstant)).expiration(
                Date.from(expirationInstant)).signWith(generateKey()).claims(getClaims(user, authentication)).compact();
    }

    public boolean isTokenValid(String token) {
        return StringUtils.hasText(token) && this.validateToken(token);
    }

    public String getTokenSubject(String token) {
        Claims claims = Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(generateKey()).build().parse(token);
            return true;
        } catch (Exception ex) {
            logger.error("Invalid token!: {}", ex.getMessage(), ex);
            return false;
        }
    }

    private Map<String, ?> getClaims(User user, Authentication authentication) {
        return Map.of(USER_CLAIM_KEY, getUserClaimValue(user), AUTHORITIES_CLAIM_KEY, authentication.getAuthorities());
    }

    private UserLoginResponseDTO getUserClaimValue(User user) {
        return new UserLoginResponseDTO(user.getId(), user.getEmail());
    }

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}
