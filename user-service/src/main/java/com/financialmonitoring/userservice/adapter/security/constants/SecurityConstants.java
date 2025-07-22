package com.financialmonitoring.userservice.adapter.security.constants;

public class SecurityConstants {

    public static final String[] PUBLIC_ROUTES =
            new String[] {
                "/api/v1/auth/register", "/api/v1/auth/login", "/api/v1/auth/verify-token"
            };

    public static final int BCRYPT_SALT = 10;
}
