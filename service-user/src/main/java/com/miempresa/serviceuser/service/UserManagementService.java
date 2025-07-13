package com.miempresa.serviceuser.service;

import com.miempresa.serviceuser.client.RoleClient;
import com.miempresa.serviceuser.dto.PaginatedResponse;
import com.miempresa.serviceuser.dto.management.UserManagementRequest;
import com.miempresa.serviceuser.model.User;
import com.miempresa.serviceuser.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserManagementService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleClient roleClient;

    public UserManagementService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleClient roleClient) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleClient = roleClient;
    }

    public void validateCredentials(String permission){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("You must be authenticated to perform this action");
        }

        List<String> permissionsClaim = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        boolean hasPermission = permissionsClaim.contains(permission);

        if (!hasPermission) {
            throw new AccessDeniedException("You don't have permission: " + permission);
        }
    }

    public Boolean promote(Long actualRole, Long newRole){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            throw new AccessDeniedException("You must be authenticated to perform this action.");
        }

        if(actualRole.equals(newRole)){
            throw new IllegalArgumentException("The User already has this role: " + actualRole);
        }

        String username = auth.getName();

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        Long defaultRole = roleClient.getDefaultRole();

        //If the role to promote is equal to the role from the user who sends the request
        //Or the role to promote is the default role
        if (currentUser.getRole().equals(newRole) || newRole.equals(defaultRole)) {
            if(actualRole.equals(defaultRole)){ //The user to promote has the default role (user)
                return true;

                // If a user is being downgraded to "user",
                // the one making the request must have the same role as the user's current role.
                // (e.g. a "chef" can only downgrade another "chef")
            }else if (newRole.equals(defaultRole) && currentUser.getRole().equals(actualRole)){
                return true;
            }
        }

        //If the user who send the request has the credentials to promote to any role
        try {
            validateCredentials("promoteAll");
            return true;

        }catch(AccessDeniedException e) {
            throw new AccessDeniedException("You Don't have the permission to promote to that role");
        }
    }


    public PaginatedResponse<User> findAllUsers(Integer page, Integer limit, Long role){
        Pageable pageable = PageRequest.of(page, limit);

        Page<User> usersPage;

        if(role == null){
            usersPage = userRepository.findAll(pageable);
        }else{
            usersPage = userRepository.findAllByRole(pageable, role);
        }

        return new PaginatedResponse<>(
                usersPage.getContent(),
                usersPage.getNumber(),
                usersPage.getTotalPages(),
                usersPage.getTotalElements(),
                usersPage.getSize()
        );
    }

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not find any user with id: " + id));
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Not find any user with username: " + username));
    }

    public User updateUser(Long id, UserManagementRequest input){
        User actualUser = findById(id);

        if(input.getPassword() != null && !input.getPassword().isBlank()){
            //Validate the credentials to change passwrod
            try{
                validateCredentials("editPassword");

            }catch(AccessDeniedException e){
                throw new AccessDeniedException("You don't have the permission to change password");
            }

            actualUser.setPassword(passwordEncoder.encode(input.getPassword()));
        }

        //Promote the Role
        if(input.getRole() != null){
            if(promote(actualUser.getRole(), input.getRole())){
                actualUser.setRole(input.getRole());
            }else{
                throw new AccessDeniedException("You Don't have the permission to promote to that role");
            }
        }

        actualUser.setUpdatedAt(new Date(System.currentTimeMillis()));

        return userRepository.save(actualUser);
    }
}
