package com.miempresa.serviceuser.service;

import com.miempresa.serviceuser.client.IndexClient;
import com.miempresa.serviceuser.dto.PaginatedResponse;
import com.miempresa.serviceuser.dto.UserPatchRequest;
import com.miempresa.serviceuser.dto.UserRequest;
import com.miempresa.serviceuser.enums.RoleEnum;
import com.miempresa.serviceuser.model.UserModel;
import com.miempresa.serviceuser.repository.UserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;


public class UserService {

    private final IndexClient indexClient;
    private final UserCrudRepository userCrudRepository;

    @Autowired
    public UserService(IndexClient indexClient, UserModel userModel, UserCrudRepository userCrudRepository) {
        this.indexClient = indexClient;
        this.userCrudRepository = userCrudRepository;
    }

    public PaginatedResponse<UserModel> findAllUsers(Integer page, Integer limit, RoleEnum role){
        //Solo para admins
        Pageable pageable = PageRequest.of(page, limit);

        Page<UserModel> usersPage;

        if(role == null){
            usersPage = userCrudRepository.findAll(pageable);
        }else{
            usersPage = userCrudRepository.findAllByRole(pageable, role);
        }

        return new PaginatedResponse<>(
                usersPage.getContent(),
                usersPage.getNumber(),
                usersPage.getTotalPages(),
                usersPage.getTotalElements(),
                usersPage.getSize()
        );
    }

    public UserModel findUser(Long id){
        return userCrudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not find any user with id: " + id));
    }

    public UserModel findUserByUsername(String username){
        return userCrudRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Not find any user with username: " + username));

    }

    public UserModel createUser(UserRequest request){

        //Buscar si el username ya existe
        userCrudRepository.findByUsername(request.getUsername())
                            .ifPresent(user -> {
                                throw new IllegalArgumentException("Username already exists");
                            });

        //Validar si la solicitud la hace un admin, o usuario privilegiado
        //para cambiar el rol de otro usuario:
        RoleEnum role = RoleEnum.USER;

        UserModel user = UserModel.builder()
                .username(request.getUsername())
                .name(request.getName())
                .password(request.getPassword())
                .role(role)
                .build();

        user.setId(indexClient.getIndex());

        return userCrudRepository.save(user);
    }

    public UserModel replaceUser(Long id, UserRequest request){
        UserModel actualUser;

        if(id == null){
            throw new IllegalArgumentException("No id was provided");
        }

        //Buscar si el username ya existe
        userCrudRepository.findByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Username already exists");
                });

        System.out.println("Id of the User to be replaced: " + id);

        actualUser = userCrudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not find any user with id: " + id));

        System.out.println("User to be replace was Found");

        //Validar si la solicitud la hace un admin, o usuario privilegiado
        //para cambiar el rol de otro usuario:
        RoleEnum role = RoleEnum.USER;

        UserModel user = UserModel.builder()
                .id(actualUser.getId())
                .username(request.getUsername())
                .name(request.getName())
                .password(request.getPassword())
                .role(role)
                .build();

        return userCrudRepository.save(user);
    }

    public UserModel updateUser(Long id, UserPatchRequest request){
        UserModel actualUser;

        if(id == null){
            throw new IllegalArgumentException("No id was provided");
        }

        //Buscar si el username ya existe
        userCrudRepository.findByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Username already exists");
                });

        System.out.println("Id of the User to be updated: " + id);

        actualUser = userCrudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not find any user with id: " + id));

        System.out.println("User to be update was Found");

        if(request.getName() != null){
            actualUser.setName(request.getName());
        }

        if(request.getUsername() != null){
            actualUser.setUsername(request.getUsername());
        }

        if(request.getPassword() != null){
            actualUser.setPassword(request.getPassword() );
        }

        //Validar si la solicitud la hace un admin, o usuario privilegiado
        //para cambiar el rol de otro usuario:
        RoleEnum role = RoleEnum.USER;

        return userCrudRepository.save(actualUser);
    }

    public void deleteUser(Long id){
        userCrudRepository.deleteById(id);
    }

}
