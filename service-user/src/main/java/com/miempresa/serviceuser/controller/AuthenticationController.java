package com.miempresa.serviceuser.controller;

import com.miempresa.serviceuser.client.RoleClient;
import com.miempresa.serviceuser.dto.user.UserRequest;
import com.miempresa.serviceuser.dto.login.LoginRequest;
import com.miempresa.serviceuser.dto.user.UserView;
import com.miempresa.serviceuser.jwt.JwtService;
import com.miempresa.serviceuser.model.User;
import com.miempresa.serviceuser.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final UserService userService;
    private final RoleClient roleClient;

    public AuthenticationController(JwtService jwtService, UserService userService, RoleClient roleClient) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.roleClient = roleClient;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginRequest login){

        String token = userService.login(login.getUsername(), login.getEmail(), login.getPassword());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<UserView> postMyUser(@Valid @RequestBody UserRequest request){

        UserView user = userService.createUser(request);

        //return ResponseEntity.ok("The User was created successfully");

        return ResponseEntity.ok(user);
    }

}
