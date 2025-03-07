package com.financialmonitoring.userservice.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

public class LoginRequestDTO implements Serializable {

    @Serial private static final long serialVersionUID = 7320557890390853832L;

    @Email @NotBlank private String email;

    @NotBlank
    @Length(min = 6)
    private String password;

    public LoginRequestDTO() {}

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
