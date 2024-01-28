package com.project.threeam.controllers;


import com.project.threeam.dtos.UserDTO;

import com.project.threeam.entities.UserEntity;
import com.project.threeam.requests.auth.LoginRequest;
import com.project.threeam.response.CustomStatusResponse;
import com.project.threeam.response.auth.AuthResponse;
import com.project.threeam.response.auth.TokenRespone;
import com.project.threeam.services.UserService;
import com.project.threeam.utils.GetDataErrorUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/User")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomStatusResponse customStatusResponse;

    @Autowired
    private GetDataErrorUtils getDataErrorUtils;

    @GetMapping("/GetUserList")
    public ResponseEntity<List<UserDTO>> getAllUser() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            if (users.isEmpty()) {
                return customStatusResponse.NOTFOUND404("No User found");

            }
            return customStatusResponse.OK200("Get List of User Successfully", users);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }


    @GetMapping("/GetUser/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        try {
            UserDTO UserDTO = userService.getUserById(userId);
            if (UserDTO == null) {
                return customStatusResponse.NOTFOUND404("User not found");
            }
            return customStatusResponse.OK200("User found", UserDTO);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }

    }


    @GetMapping("/GetUserByVerifyCode")
    public ResponseEntity<UserDTO> getUserByCode(@Param("code") String code) {
        try {
            UserDTO UserDTO = userService.getUserByVerificationCode(code);
            if (UserDTO == null) {
                return customStatusResponse.NOTFOUND404("User not found");
            }
            return customStatusResponse.OK200("User found", UserDTO);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }

    }

    @PutMapping("/UpdateUser/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody @Valid UserDTO userDTO, BindingResult rs) {
        try {
            if(rs.hasErrors()){
                var errors = getDataErrorUtils.DataError(rs);
                return customStatusResponse.BADREQUEST400("Provider data is incorrect",errors);
            }
            UserDTO updatedUser = userService.updateUser(userId, userDTO);
            if (updatedUser == null) {
                return customStatusResponse.NOTFOUND404("User not found");
            }
            return customStatusResponse.OK200("User updated", updatedUser);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }


    @DeleteMapping("/DeleteUser/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            boolean checkUser =   userService.deleteUser(userId);
            if (checkUser) {
                return customStatusResponse.OK200("User deleted");
            } else {
                return customStatusResponse.NOTFOUND404("User not found");
            }
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @PostMapping("/AddUser")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO, HttpServletRequest request, BindingResult rs) {
        try {
            if(rs.hasErrors()){
                var errors = getDataErrorUtils.DataError(rs);
                return customStatusResponse.BADREQUEST400("Provider data is incorrect",errors);
            }
            String userDTOEmail = userDTO.getEmail();
            String originalUrl = request.getRequestURL().toString();
            UserDTO userDTOCheck = userService.getUserByEmail(userDTOEmail);
            if(userDTOCheck != null ) {
                return customStatusResponse.BADREQUEST400("Email is Aldredy used");
            }

            UserDTO createdUser = userService.createUser(userDTO,originalUrl);
            if(createdUser == null ){
                return customStatusResponse.BADREQUEST400("Create user faild");

            }
            return customStatusResponse.CREATED201("User created", createdUser);
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }

    }

    @GetMapping("/AddUser/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @GetMapping("/Reset-Password")
    public ResponseEntity<String> ResetUser(@RequestBody String email,@RequestBody String url) {
        try {
            UserDTO UserDTO = userService.getUserByEmail(email);
            if (UserDTO == null) {
                return customStatusResponse.NOTFOUND404("Email does not exist, please re-enter email");
            }
            Boolean rs = userService.updatePassword(email,url);
            if(rs) {
                return customStatusResponse.OK200("Please enter your email to confirm");
            } else {
                return customStatusResponse.BADREQUEST400("Provider data is incorrect");
            }
        } catch (Exception e) {
            return customStatusResponse.INTERNALSERVERERROR500(e.getMessage());
        }
    }

    @PostMapping("/login")
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
