package com.miempresa.servicerole.controller;

import com.miempresa.servicerole.dto.RolePatchRequest;
import com.miempresa.servicerole.dto.RoleRequest;
import com.miempresa.servicerole.model.Role;
import com.miempresa.servicerole.service.RoleService;
import com.miempresa.servicerole.utils.converter.ObjectConverter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {

        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRoles(@RequestParam(required = false) String name){
        if(name == null || name.isBlank()){
            return ResponseEntity.ok(roleService.findAllRoles());
        }else{
            return ResponseEntity.ok(roleService.findByName(name));
        }
    }

    @GetMapping("/getDefault")
    public ResponseEntity<Long> getDefaultRole(){
        Role role = roleService.findDefaultRole();
        return ResponseEntity.ok(role.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRole(@PathVariable Long id,
                                        @RequestParam(required = false) String fields) {

        Role role = roleService.findById(id);

        if(fields == null || fields.isBlank()){
            return ResponseEntity.ok(role);
        }else{
            Map<String, Object> response = ObjectConverter.RoleToJson(role, fields);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping
    public ResponseEntity<Role> postRole(@Valid @RequestBody RoleRequest roleRequest){
        return ResponseEntity.ok(roleService.createRole(roleRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> putRole(@PathVariable Long id, @Valid @RequestBody RoleRequest roleRequest){
        return ResponseEntity.ok(roleService.replaceRole(id, roleRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Role> patchRole(@PathVariable Long id, @Valid @RequestBody RolePatchRequest request){
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id){
        roleService.deleteById(id);
        return ResponseEntity.ok("Role deleted Successfully");
    }

}
