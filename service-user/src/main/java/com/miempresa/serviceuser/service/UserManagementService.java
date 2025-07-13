package com.miempresa.serviceuser.service;

import com.miempresa.serviceuser.dto.PaginatedResponse;
import com.miempresa.serviceuser.dto.UserRequest;
import com.miempresa.serviceuser.enums.RoleEnum;
import com.miempresa.serviceuser.model.Role;
import com.miempresa.serviceuser.model.User;
import com.miempresa.serviceuser.repository.RoleRepository;
import com.miempresa.serviceuser.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UserManagmentService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public UserManagmentService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void validateCredentials(Function<Role, Boolean> permissionExtractor){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!auth.isAuthenticated()) {
            throw new AccessDeniedException("You don't have the credentials for this operation");
        }

        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException("Not found any role whit username: " + username));

        Role userRole = roleRepository.findById(user.getRole())
                .orElseThrow(() ->
                        new IllegalArgumentException("The role of the username doesn't exit any more. Id of the role: " + user.getRole()));

        Boolean hasPermission = permissionExtractor.apply(userRole);
        if(hasPermission == null || !hasPermission){
            throw new AccessDeniedException("");
        }
    }


    public PaginatedResponse<User> findAllUsers(Integer page, Integer limit, RoleEnum role){
        //Solo para admins
        validateCredentials(Role::getManageUser);

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
        validateCredentials(Role::getManageUser);

        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not find any user with id: " + id));
    }

    public User findByUsername(String username){
        validateCredentials(Role::getManageUser);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Not find any user with username: " + username));
    }

    public User updateUser(Long id, UserRequest input){

        validateCredentials(Role::getManageUser);

        User actualUser = findById(id);

        //Validate doesn't exist the new username
        String username = input.getUsername();
        //if want to change the username
        if(!actualUser.getUsername().equals(username)) {
            Optional<User> aux = userRepository.findByUsername(username));

            if(aux.isPresent()){
                throw new IllegalArgumentException("The username: " + username + "already exists");
            }else{
                actualUser.setUsername(username);
            }
        }

        if(!input.getPassword().isBlank()){
            //Validate the credentials to change passwrod
            try{
                validateCredentials(Role::getEditPassword);

            }catch(AccessDeniedException e){
                throw new AccessDeniedException("Don't have the permission to change password");
            }

            actualUser.setPassword(input.getPassword());
        }

        if(!input.getRole())

        //Change the attributes
        actualUser.setFullname(input.getFullname());
        actualUser.setUpdatedAt(new Date(System.currentTimeMillis()));

        return userRepository.save(actualUser);
    }




    public void deleteById(Long id){
        validateCredentials(Role::getManageUser);

        userRepository.deleteById(id);
    }

    public void deleteByUsername(String username){
        validateCredentials(Role::getManageUser);

        userRepository.deleteByUsername(username);
    }
}
