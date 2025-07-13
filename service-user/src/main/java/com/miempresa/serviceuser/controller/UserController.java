package com.miempresa.serviceuser.controller;


import com.miempresa.serviceuser.dto.UserPatchRequest;
import com.miempresa.serviceuser.dto.UserRequest;
import com.miempresa.serviceuser.model.User;
import com.miempresa.serviceuser.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user/me")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<User> getMyUser(){
        return ResponseEntity.ok(userService.findMyUser());
    }

    @PatchMapping
    public ResponseEntity<User> patchMyUser(@RequestBody UserPatchRequest request){
        System.out.println(request);
        return ResponseEntity.ok(userService.updateUser(request));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMyUser(){
        userService.deleteUser();
        return ResponseEntity.ok("The user was deleted successfully");
    }
}
