package com.miempresa.servicerole.utils.converter;

import com.miempresa.servicerole.model.Role;

import java.util.HashMap;
import java.util.Map;

public class ObjectConverter {

    public static Map<String, Object> RoleToJson(Role role, String fields){
        String[] fieldList = fields.split(",");

        Map<String, Object> json = new HashMap<>();
        //Revisar cada campo
        for (String field : fieldList) {
            if (field.trim().isEmpty()) {
                continue; // Salta al siguiente campo
            }

            //Buscar cada campo a que atributo se refiere y agregarlo al json
            switch (field.trim().toLowerCase()) {
                case "id":
                    json.put("id", role.getId());
                    break;
                case "name":
                    json.put("name", role.getName());
                    break;
                case "default":
                    json.put("default", role.isDefault());
                    break;
                case "manageproduct":
                    json.put("manageproduct", role.isManageProduct());
                    break;
                case "manageorder":
                    json.put("manageorder", role.isManageOrder());
                    break;
                case "manageuser":
                    json.put("manageuser", role.isManageUser());
                    break;
                case "managerole":
                    json.put("managerole", role.isManageRole());
                    break;
                case "promoteall":
                    json.put("promoteall", role.isPromoteAll());
                    break;
                case "editpassword":
                    json.put("editpassword", role.isEditPassword());
                    break;
            }
        }

        return json;
    }

}
