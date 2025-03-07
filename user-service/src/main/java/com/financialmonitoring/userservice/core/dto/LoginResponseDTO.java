package com.financialmonitoring.userservice.core.dto;

import java.io.Serial;
import java.io.Serializable;

public class LoginResponseDTO implements Serializable {

    @Serial private static final long serialVersionUID = -7840475044306892381L;

    private String token;
    private UserLoginResponseDTO user;

    public LoginResponseDTO(String token, UserLoginResponseDTO user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserLoginResponseDTO getUser() {
        return user;
    }

    public void setUser(UserLoginResponseDTO user) {
        this.user = user;
    }

    public static class UserLoginResponseDTO implements Serializable {

        @Serial private static final long serialVersionUID = -7869448072367361948L;

        private Long id;
        private String email;

        public UserLoginResponseDTO() {}

        public UserLoginResponseDTO(Long id, String email) {
            this.id = id;
            this.email = email;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
