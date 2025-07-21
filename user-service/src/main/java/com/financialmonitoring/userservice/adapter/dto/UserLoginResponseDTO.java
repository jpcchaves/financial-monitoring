package com.financialmonitoring.userservice.adapter.dto;

import java.io.Serial;
import java.io.Serializable;

public class UserLoginResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7869448072367361948L;

    private Long id;
    private String email;

    public UserLoginResponseDTO() {
    }

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
