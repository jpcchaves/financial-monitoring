package com.financialmonitoring.userservice.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

public class LoginRequestDTO implements Serializable {

    @Serial private static final long serialVersionUID = 7320557890390853832L;

    @Email(message = "Invalid email!")
    @NotBlank(message = "Email is a required field!")
    private String email;

    @NotBlank(message = "Password is a required field!")
    @Length(min = 6, message = "The password must cointain at least 6 characters!")
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
