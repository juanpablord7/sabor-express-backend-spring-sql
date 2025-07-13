package com.miempresa.serviceuser.service;

import com.miempresa.serviceuser.dto.role.RoleRequest;
import com.miempresa.serviceuser.model.Role;
import com.miempresa.serviceuser.model.User;
import com.miempresa.serviceuser.repository.RoleRepository;
import com.miempresa.serviceuser.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private final ModelMapper modelMapper;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(ModelMapper modelMapper, RoleRepository roleRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void validateCredentials(){
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

        if(!userRole.getManageRole()){
            throw new AccessDeniedException("");
        }
    }


    public Role findById(Long id){
        validateCredentials();

        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found any role whit id: " + id));
    }

    public Role findByName(String name){
        validateCredentials();

        return roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Not found any role whit name: " + name));
    }

    public Role createRole(RoleRequest request){
        validateCredentials();

        Role role = modelMapper.map(request, Role.class);

        //Check is this is the new default role
        if(request.isDefault()){
            //Find if there is another default role
            Optional<Role> actualDefault = roleRepository.findByIsDefault(true);
            if(actualDefault.isPresent()){
                //Set the last default role to false
                actualDefault.get().setIsDefault(false);

                //Save that role like not default
                roleRepository.save(actualDefault.get());
            }
        }

        return roleRepository.save(role);
    }

    public Role replaceRole(RoleRequest request){
        validateCredentials();

        Role actualRole = roleRepository.findByName(request.getName())
                .orElseThrow(() -> new IllegalArgumentException("Not found a role with the name: " + request.getName()));

        Role role = modelMapper.map(request, Role.class);

        //Check is this is the new default role
        if(request.isDefault()){
            //Find if there is another default role
            Optional<Role> actualDefault = roleRepository.findByIsDefault(true);
            if(actualDefault.isPresent()){
                //Set the last default role to false
                actualDefault.get().setIsDefault(false);

                //Save that role like not default
                roleRepository.save(actualDefault.get());
            }
        }

        role.setId(actualRole.getId());

        return roleRepository.save(role);
    }

    public void deleteById(Long id){
        validateCredentials();

        Optional<Role> role = roleRepository.findById(id);

        //Check is this is the default role, then won't be deleted
        if(role.isPresent() && role.get().getIsDefault()){
            throw new IllegalArgumentException("This is the default role, can't be deleted, first create or define another role as the default");
        }

        roleRepository.deleteById(id);
    }

    public void deleteByName(String name){
        validateCredentials();

        Optional<Role> role = roleRepository.findByName(name);

        //Check is this is the default role, then won't be deleted
        if(role.isPresent() && role.get().getIsDefault()){
            throw new IllegalArgumentException("This is the default role, can't be deleted, first create or define another role as the default");
        }

        roleRepository.deleteByName(name);
    }

}
