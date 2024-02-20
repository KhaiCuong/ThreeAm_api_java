package com.project.threeam.requests.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String password;
    private String code;

}
