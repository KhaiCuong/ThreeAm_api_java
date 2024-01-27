package com.project.threeam.response.auth;

import com.project.threeam.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenRespone {

    private String token;
    private UserEntity userToken;
}
