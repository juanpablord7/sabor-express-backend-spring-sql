package com.miempresa.serviceuser.service;

import com.miempresa.serviceuser.enums.RoleEnum;
import com.miempresa.serviceuser.model.UserModel;
import com.miempresa.serviceuser.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;

    public AuthService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public RoleEnum findRole(String token){
        Claims claims = jwtUtil.extractClaims(token);
        RoleEnum role = claims.get("role", RoleEnum.class);
        return role != null ? role : RoleEnum.USER;
    }

    public Claims findClaims(String token){
        return jwtUtil.extractClaims(token);
    }

    public String createToken(UserModel user){
        return jwtUtil.generateToken(user);
    }

}
