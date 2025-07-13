package com.miempresa.serviceuser.utils.converter;

import com.miempresa.serviceuser.model.User;

import java.util.HashMap;
import java.util.Map;

public class ObjectConverter {

    public static Map<String, Object> UserToJson(User user, String fields) {
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
                    json.put("id", user.getId());
                    break;
                case "username":
                    json.put("username", user.getUsername());
                    break;
                case "fullname":
                    json.put("fullname", user.getFullname());
                    break;
                case "email":
                    json.put("email", user.getEmail());
                    break;
                case "created_at":
                    json.put("createdAt", user.getCreatedAt());
                    break;
                case "updated_at":
                    json.put("updatedAt", user.getUpdatedAt());
                    break;
                case "role":
                    json.put("role", user.getRole());
                    break;
            }
        }

        return json;
    }
}
