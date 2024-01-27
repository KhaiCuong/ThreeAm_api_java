package com.project.threeam.dtos;

import com.project.threeam.entities.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO extends BaseDTO {

    private Long userId;

    @NotEmpty(message = "Fullname is not empty")
    private String fullname;

    @NotEmpty(message = "Email is not empty")
    @Email(message = "Email format is wrong")
    private String email;

    @NotEmpty(message = "Phone number is not empty")
    @Size(min = 7,  max = 12, message = "Phone number must be 7-12 characters")
    private String phone_number;

    private RoleEnum role;

    @NotEmpty(message = "Address is not empty")
    private String address;

    @NotEmpty(message = "Password is not empty")
    private String password;

    private Boolean verify;
}
