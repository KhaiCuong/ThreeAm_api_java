package com.project.threeam.requests.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty(message = "Email is not empty")
    @Email(message = "Email format is wrong")
    private String email;

    @NotEmpty(message = "Password is not empty")
    private String password;
}
