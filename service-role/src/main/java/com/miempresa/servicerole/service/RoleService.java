package com.miempresa.servicerole.service;

import com.miempresa.servicerole.dto.RolePatchRequest;
import com.miempresa.servicerole.dto.RoleRequest;
import com.miempresa.servicerole.model.Role;
import com.miempresa.servicerole.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final ModelMapper modelMapper;

    private final RoleRepository roleRepository;

    public RoleService(ModelMapper modelMapper, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }

    public Role findDefaultRole(){
        return roleRepository.findByIsDefault(true)
                .orElseThrow(() -> new IllegalArgumentException("Not found the default role"));
    }

    public Role findById(Long id){

        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found any role whit id: " + id));
    }

    public Role findByName(String name){

        return roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Not found any role whit name: " + name));
    }

    public Role createRole(RoleRequest request){

        Role role = Role.builder()
                .name(request.getName())
                .isDefault(request.isDefault())
                .manageProduct(request.isManageProduct())
                .manageOrder(request.isManageOrder())
                .manageUser(request.isManageUser())
                .manageRole(request.isManageRole())
                .promoteAll(request.isPromoteAll())
                .editPassword(request.isEditPassword())
                .chef(request.isChef())
                .delivery(request.isDelivery())

                .build();

        //Check is this is the new default role
        if(request.isDefault()){
            //Find if there is another default role
            Optional<Role> actualDefault = roleRepository.findByIsDefault(true);
            if(actualDefault.isPresent()){
                //Set the last default role to false
                actualDefault.get().setDefault(false);

                //Save that role like not default
                roleRepository.save(actualDefault.get());
            }
        }

        return roleRepository.save(role);
    }

    public Role replaceRole(Long id, RoleRequest request){

        Role actualRole = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found a role with the id: " + id));

        Role role = modelMapper.map(request, Role.class);

        //Check is this is the new default role
        if(request.isDefault()){
            //Find if there is another default role
            Optional<Role> actualDefault = roleRepository.findByIsDefault(true);
            if(actualDefault.isPresent()){
                //Set the last default role to false
                actualDefault.get().setDefault(false);

                //Save that role like not default
                roleRepository.save(actualDefault.get());
            }
        }

        role.setId(actualRole.getId());

        return roleRepository.save(role);
    }

    public Role updateRole(Long id, RolePatchRequest request){

        Role actualRole = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found a role with the id: " + id));


        if(request.getName() != null && !request.getName().isBlank()){
            actualRole.setName(request.getName());
        }
        if (request.getManageProduct() != null) {
            actualRole.setManageProduct(request.getManageProduct());
        }

        if (request.getManageOrder() != null) {
            actualRole.setManageOrder(request.getManageOrder());
        }

        if (request.getManageUser() != null) {
            actualRole.setManageUser(request.getManageUser());
        }

        if (request.getManageRole() != null) {
            actualRole.setManageRole(request.getManageRole());
        }

        if (request.getPromoteAll() != null) {
            actualRole.setPromoteAll(request.getPromoteAll());
        }

        if (request.getEditPassword() != null) {
            actualRole.setEditPassword(request.getEditPassword());
        }


        if(request.getIsDefault() != null) {
            boolean isDefault = request.getIsDefault();
            actualRole.setDefault(isDefault);

            //Check is this is the new default role
            if(isDefault){
                //Find if there is another default role
                Optional<Role> actualDefault = roleRepository.findByIsDefault(true);
                if(actualDefault.isPresent()){
                    //Set the last default role to false
                    actualDefault.get().setDefault(false);

                    //Save that role like not default
                    roleRepository.save(actualDefault.get());
                }
            }
        }

        return roleRepository.save(actualRole);
    }

    public void deleteById(Long id){

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The role doesn't exist") );

        //Check is this is the default role, then won't be deleted
        if (role.isDefault()) {
            throw new IllegalArgumentException("This is the default role, can't be deleted, first create or define another role as the default");
        }

        roleRepository.deleteById(id);
    }

    public void deleteByName(String name){

        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("The role doesn't exist") );

        //Check is this is the default role, then won't be deleted
        if(role.isDefault()){
            throw new IllegalArgumentException("This is the default role, can't be deleted, first create or define another role as the default");
        }

        roleRepository.deleteByName(name);
    }

}
