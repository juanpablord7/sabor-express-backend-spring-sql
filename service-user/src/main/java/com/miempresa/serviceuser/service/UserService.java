package com.miempresa.serviceuser.service;

import com.miempresa.serviceuser.client.RoleClient;
import com.miempresa.serviceuser.dto.UserPatchRequest;
import com.miempresa.serviceuser.dto.UserRequest;
import com.miempresa.serviceuser.dto.client.RoleResponse;
import com.miempresa.serviceuser.jwt.JwtService;
import com.miempresa.serviceuser.model.User;
import com.miempresa.serviceuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final RoleClient roleClient;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RoleClient roleClient) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleClient = roleClient;
    }

    public User findMyUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AccessDeniedException("Not found any User with username: " + username));
    }

    public String login(String username, String email, String password){
        User user;

        //Validate the different way to login (Username or email)
        if(username != null && !username.isBlank()) {
            user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("Not found any User with username: " + username));
        } else if (email != null && !email.isBlank()) {
            user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("Not found any User with email: " + email));
        }else{
            throw new IllegalArgumentException("Don't was provided 'username' or 'email'");
        }

        System.out.println(username);

        //Check if the password is correct
        if(passwordEncoder.matches(password, user.getPassword())){
            //Get the role permissions for the jwt
            RoleResponse role = roleClient.getRole(user.getRole());

            return jwtService.generateToken(user, role);
        }else{
            throw new IllegalArgumentException("The Password is incorrect");
        }
    }

    public User createUser(UserRequest request){

        Long role = roleClient.getDefaultRole();
        if(role == null){
            role = 0L;
        }

        User user = User.builder()
                .username(request.getUsername())
                .fullname(request.getFullname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(new Date(System.currentTimeMillis()))
                .updatedAt(new Date(System.currentTimeMillis()))
                .role(role)
                .build();

        return userRepository.save(user);
    }

    public User replaceUser(UserRequest request){
        User user = findMyUser();

        System.out.println("Id of the User to be replaced: " + user.getId());

        //If want to change the username
        String username = request.getUsername();

        if(!user.getUsername().equals(username)){
            userRepository.findByUsername(username)
                            .ifPresent(aux -> {
                                throw new IllegalArgumentException("Username already exists");
                            });

            user.setUsername(username);
        }

        //If want to change the email
        String email = request.getEmail();
        if(!user.getEmail().equals(email)){
            userRepository.findByUsername(email)
                    .ifPresent(aux -> {
                        throw new IllegalArgumentException("Username already exists");
                    });

            user.setEmail(email);
        }

        //Change the attributes
        user.setFullname(request.getFullname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUpdatedAt(new Date(System.currentTimeMillis()));

        return userRepository.save(user);
    }

    public User updateUser(UserPatchRequest request){
        User user = findMyUser();

        System.out.println("Id of the User to be updated: " + user.getId());

        //If want to change the username
        String username = request.getUsername();
        if(username != null && !username.isBlank()){
            if(!user.getUsername().equals(username)){
                userRepository.findByUsername(username)
                        .ifPresent(aux -> {
                            throw new IllegalArgumentException("Username already exists");
                        });

                user.setUsername(username);
            }
        }


        //If want to change the email
        String email = request.getEmail();
        if(email != null && !email.isBlank()) {
            userRepository.findByEmail(email)
                    .ifPresent(aux -> {
                        throw new IllegalArgumentException("Username already exists");
                    });
            user.setEmail(email);
        }

        //Change the attributes
        String fullname = request.getFullname();
        if(fullname != null && !fullname.isBlank()){
            user.setFullname(request.getFullname());
        }

        String password = request.getPassword();
        if(password != null && !password.isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        System.out.println("username");
        System.out.println(username != null);
        System.out.println(username != null && !username.isBlank());
        System.out.println("fullname");
        System.out.println(fullname);
        System.out.println(fullname != null);
        System.out.println(fullname != null && !fullname.isBlank());
        System.out.println("email");
        System.out.println(email != null);
        System.out.println(email != null && !email.isBlank());
        System.out.println("password");
        System.out.println(password != null);
        System.out.println(password != null && !password.isBlank());

        user.setUpdatedAt(new Date(System.currentTimeMillis()));

        return userRepository.save(user);
    }

    public void deleteUser(){
        User user = findMyUser();

        userRepository.deleteById(user.getId());
    }

}
