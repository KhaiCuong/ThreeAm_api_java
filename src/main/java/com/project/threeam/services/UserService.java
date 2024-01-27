package com.project.threeam.services;

import com.project.threeam.dtos.CategoryDTO;
import com.project.threeam.dtos.ProductDTO;
import com.project.threeam.dtos.UserDTO;
import com.project.threeam.entities.CategoryEntity;
import com.project.threeam.entities.ProductEntity;
import com.project.threeam.entities.UserEntity;
import com.project.threeam.repositories.UserRepository;
import com.project.threeam.requests.auth.LoginRequest;
import com.project.threeam.response.auth.AuthResponse;
import com.project.threeam.response.auth.TokenRespone;
import com.project.threeam.services.mail.MailUserVerificationCode;
import com.project.threeam.utils.JwtUtil;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    MailUserVerificationCode mailUserVerificationCode;



    @Autowired
    private AuthenticationManager authenticationManager;

    public List<UserDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(this::convertToDTO).toList();

    }
    public UserDTO getUserById(Long userId) {
        Optional<UserEntity> user = userRepository.findByUserId(userId);
        return user.map(this::convertToDTO).orElse(null);
    }

    public UserDTO getUserByVerificationCode(String code) {
        Optional<UserEntity> user = userRepository.findByVerificationCode(code);
        return user.map(this::convertToDTO).orElse(null);
    }

    public UserDTO getUserByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.map(this::convertToDTO).orElse(null);
    }

    public UserEntity getByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return  user.get();
    }


    public UserDTO createUser(UserDTO userDTO, String originalUrl) {
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity.setVerify(false);

        String randomCode = RandomString.make(64);
        userEntity.setVerificationCode(randomCode);
        UserEntity savedEntity = userRepository.save(userEntity);
        sendVerificationEmail(savedEntity, originalUrl);

        return convertToDTO(savedEntity);
    }

    public Boolean updatePassword(String email, String originalUrl) {
        Optional<UserEntity> existingUserOptional = userRepository.findByEmail(email);
        UserEntity userEntity = existingUserOptional.get();

        if(existingUserOptional.isPresent()) {
            String randomCode = RandomString.make(64);
            userEntity.setVerificationCode(randomCode);
            UserEntity savedEntity = userRepository.save(userEntity);
            sendResetPassEmail(savedEntity, originalUrl);
            return  true;
        }
        return false;
    }

    private void sendResetPassEmail(UserEntity user,String originalUrl)
    {

        String content = "Dear [[name]],<br>"
                + "Please click the link below to Reset Password:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "ThreeAM Store";


        content = content.replace("[[name]]", user.getFullname());
        String verifyURL = originalUrl + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);


        mailUserVerificationCode.resetPassMail(user.getEmail(), content);

    }

    private void sendVerificationEmail(UserEntity user,String originalUrl)
     {

        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "ThreeAM Store";


        content = content.replace("[[name]]", user.getFullname());
        String verifyURL = originalUrl + "/verify?code=" + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);


        mailUserVerificationCode.sendMail(user.getEmail(), content);

    }

    public Boolean verify(String verificationCode) {
        Optional<UserEntity> userOptional = userRepository.findByVerificationCode(verificationCode);
        UserEntity user = userOptional.get();
        if (user == null || user.getVerify()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setVerify(true);
            userRepository.save(user);
            return true;
        }

    }


    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        Optional<UserEntity> existingUserOptional = userRepository.findByUserId(userId);
        if (existingUserOptional.isPresent()) {
            UserEntity existingUser = existingUserOptional.get();

            existingUser.setUserId(existingUser.getUserId());
            existingUser.setRole(existingUser.getRole());
            existingUser.setVerify(existingUser.getVerify());
            existingUser.setVerificationCode(existingUser.getVerificationCode());


            if (userDTO.getFullname() != null) {
                existingUser.setFullname(userDTO.getFullname());
            }

            if (userDTO.getEmail() != null) {
                existingUser.setEmail(userDTO.getEmail());
            }


            if (userDTO.getFullname() != null) {
                existingUser.setFullname(userDTO.getFullname());
            }

            if (userDTO.getAddress() != null) {
                existingUser.setAddress(userDTO.getAddress());
            }

            if (userDTO.getPassword() != null) {
                existingUser.setPassword(userDTO.getPassword());
            }

            UserEntity savedEntity = userRepository.save(existingUser);

            return convertToDTO(savedEntity);
        } else {
            return null;
        }
    }

    public Boolean deleteUser(Long userId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(userId);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            userRepository.delete(userEntity);
            return true;
        } return false;
    }



    public TokenRespone login(LoginRequest signInRequest) throws Exception {
        try{

            UserEntity userEntity = getByEmail(signInRequest.getEmail());
            if(userEntity != null &&  userEntity.getPassword().equals(signInRequest.getPassword()) ) {

                TokenRespone tokenRespone = JwtUtil.generateToken(userEntity);
                return tokenRespone;
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new Exception("email or password incorrect");
        }
    }




    private UserDTO convertToDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }
}
