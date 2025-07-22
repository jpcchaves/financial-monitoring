package com.financialmonitoring.userservice.adapter.dto;

import java.io.Serial;
import java.io.Serializable;

public class LoginResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7840475044306892381L;

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
}
