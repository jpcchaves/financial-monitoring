package com.financialmonitoring.userservice.core.dto;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

public class RegisterRequestDTO implements Serializable {

    @NotBlank(message = "First name is a required field!")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email is a required field!")
    private String email;

    @NotBlank(message = "Password must be informed!")
    private String password;

    @NotBlank(message = "Password confirmation must be informed!")
    private String confirmPassword;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
