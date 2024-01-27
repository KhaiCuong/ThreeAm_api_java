package com.project.threeam.controllers;

import com.project.threeam.entities.UserEntity;
import com.project.threeam.requests.auth.LoginRequest;
import com.project.threeam.response.CustomStatusResponse;
import com.project.threeam.response.auth.TokenRespone;
import com.project.threeam.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomStatusResponse customStatusResponse;

    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest signInRequest) throws Exception {
        try {
            UserEntity UserEntity = userService.getByEmail(signInRequest.getEmail());
            if(!UserEntity.getVerify()) {
                return customStatusResponse.BADREQUEST400("account has not been activated!");
            }
            TokenRespone data = userService.login(signInRequest);
            if(data!= null ) {
                return customStatusResponse.OK200("login success", data);

            } else {
                return customStatusResponse.OK200("login success", data);

            }
        } catch (Exception e) {
            return  customStatusResponse.UNAUTHORIZED401(e.getMessage());
        }
    }
}
