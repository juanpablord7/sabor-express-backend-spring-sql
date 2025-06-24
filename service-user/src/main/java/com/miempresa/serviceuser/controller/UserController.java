package com.miempresa.serviceuser.controller;

import com.miempresa.serviceuser.dto.PaginatedResponse;
import com.miempresa.serviceuser.enums.RoleEnum;
import com.miempresa.serviceuser.model.UserModel;
import com.miempresa.serviceuser.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.miempresa.serviceuser.utils.converter.ObjectConverter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<UserModel>> getAllUsers(
                                                    @RequestParam(required = false) String inputRole,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int limit){

        RoleEnum role;
        if(inputRole != null){
            try {
                //Transformar role a el role correspondiente
                role = RoleEnum.valueOf(inputRole.toUpperCase());
            } catch (IllegalArgumentException e) {
                String validRoles = Arrays.stream(RoleEnum.values())
                        .map(Enum::name)
                        .collect(Collectors.joining(", "));
                throw new IllegalArgumentException("The provided role is invalid. Valid roles are: " + validRoles);
            }
        }


        PaginatedResponse<UserModel> users = userService.findAllUsers(page, limit, role);
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id,
                                             @RequestParam(required = false) String fields){

        UserModel user = userService.findUser(id);

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

    @PostMapping()
    public ResponseEntity<?> login(){
        return ResponseEntity.ok("");
    }


}
