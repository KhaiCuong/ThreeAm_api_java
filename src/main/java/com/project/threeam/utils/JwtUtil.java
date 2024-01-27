package com.project.threeam.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.threeam.entities.UserEntity;
import com.project.threeam.response.auth.TokenRespone;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    public static final String SECRET_KEY = "eProject";

    public static TokenRespone generateToken(UserEntity user) {
        String token = createToken(user.getEmail());
        return new TokenRespone(token, user);
    }

    private static String createToken(String email) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000 * 24))
                .sign(algorithm);
    }
}
