package com.miempresa.serviceuser.utils.converter;

import com.miempresa.serviceuser.model.UserModel;

import java.util.HashMap;
import java.util.Map;

public class ObjectConverter {

    public static Map<String, Object> UserToJson(UserModel user, String fields) {
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
                case "name":
                    json.put("name", user.getName());
                    break;
                case "password":
                    json.put("password", user.getPassword());
                    break;
                case "role":
                    json.put("role", user.getRole());
                    break;
            }
        }

        return json;
    }
}
