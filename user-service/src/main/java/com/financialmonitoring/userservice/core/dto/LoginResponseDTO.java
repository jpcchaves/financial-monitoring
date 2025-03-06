package com.financialmonitoring.userservice.core.dto;

import java.io.Serial;
import java.io.Serializable;

public class LoginResponseDTO implements Serializable {

    @Serial private static final long serialVersionUID = -7840475044306892381L;

    private Long id;
    private String email;

    public LoginResponseDTO() {}

    public LoginResponseDTO(Long id, String email) {
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
