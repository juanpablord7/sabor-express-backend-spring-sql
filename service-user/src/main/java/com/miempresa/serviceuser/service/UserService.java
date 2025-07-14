package com.miempresa.serviceuser.service;

import com.miempresa.serviceuser.client.RoleClient;
import com.miempresa.serviceuser.dto.user.UserPatchRequest;
import com.miempresa.serviceuser.dto.user.UserRequest;
import com.miempresa.serviceuser.dto.client.RoleResponse;
import com.miempresa.serviceuser.dto.user.UserView;
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

    public User findAuthenticatedUser(){
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

    public UserView findMyUser(){
        User user = findAuthenticatedUser();

        return userRepository.findProjectedById(user.getId())
                .orElseThrow(() -> new AccessDeniedException("Not found any User with id: " + user.getId()));
    }

    public UserView createUser(UserRequest request){

        Long role = roleClient.getDefaultRole();

        User user = User.builder()
                .username(request.getUsername())
                .fullname(request.getFullname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(new Date(System.currentTimeMillis()))
                .updatedAt(new Date(System.currentTimeMillis()))
                .role(role)
                .build();

        user = userRepository.save(user);

        return userRepository.findProjectedById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("The user was not created successfully") );
    }

    public UserView replaceUser(UserRequest request){
        User user = findAuthenticatedUser();

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

        user = userRepository.save(user);

        return userRepository.findProjectedById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("The user was not created successfully") );
    }

    public UserView updateUser(UserPatchRequest request){
        User user = findAuthenticatedUser();

        System.out.println("Id of the User to be updated: " + user.getId());

        //Validate if the username is included
        String username = request.getUsername();
        if(username != null && !username.isBlank()){
            //Search if the username is not assigned to another user
            userRepository.findByUsername(username)
                    //If is present throw error:
                    .ifPresent(aux -> {
                        throw new IllegalArgumentException("Username already exists");
                    });

            //The username is not assigned to any other user, so is set to this user
            user.setUsername(username);
        }

        //Validate if the email is included
        String email = request.getEmail();
        if(email != null && !email.isBlank()) {
            //Search if the email is not assigned to another user
            userRepository.findByEmail(email)
                    //If is present throw error:
                    .ifPresent(aux -> {
                        throw new IllegalArgumentException("Username already exists");
                    });

            //The email is not assigned to any other user, so is set to this user
            user.setEmail(email);
        }

        //Validate if the fullname is included
        String fullname = request.getFullname();
        if(fullname != null && !fullname.isBlank()){
            user.setFullname(request.getFullname());
        }

        //Validate if the password is included
        String password = request.getPassword();
        if(password != null && !password.isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user.setUpdatedAt(new Date(System.currentTimeMillis()));

        //Save the User
        user = userRepository.save(user);

        return userRepository.findProjectedById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("The user was not created successfully") );
    }

    public void deleteUser(){
        User user = findAuthenticatedUser();

        userRepository.deleteById(user.getId());
    }

}
