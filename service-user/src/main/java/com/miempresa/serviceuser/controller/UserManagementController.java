package com.miempresa.serviceuser.controller;

import com.miempresa.serviceuser.dto.PaginatedResponse;
import com.miempresa.serviceuser.dto.management.UserManagementRequest;
import com.miempresa.serviceuser.model.User;
import com.miempresa.serviceuser.service.UserManagementService;
import com.miempresa.serviceuser.utils.converter.ObjectConverter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserManagementController {
    private final UserManagementService userManagService;

    public UserManagementController(UserManagementService userManagService) {
        this.userManagService = userManagService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<User>> getAllUsers(
            @RequestParam(required = false) Long role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit){

        PaginatedResponse<User> users = userManagService.findAllUsers(page, limit, role);
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUserById (@PathVariable Long id,
                                          @RequestParam(required = false) String fields){

        User user = userManagService.findById(id);

        //Don't find any product
        if(user.getId() == null){
            return ResponseEntity.ok("");
        }

        //Filtrar por los campos solicitados
        if (fields != null && !fields.isEmpty()) {
            Map<String, Object> response = ObjectConverter.UserToJson(user, fields);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(user);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<User> patchUser(@PathVariable Long id,
                                          @RequestBody UserManagementRequest userManagRequest){
        User user = userManagService.updateUser(id, userManagRequest);

        return ResponseEntity.ok(user);
    }
}
