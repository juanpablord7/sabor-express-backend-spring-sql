package com.miempresa.servicecategory.utils.converter;

import com.miempresa.servicecategory.model.CategoryModel;

import java.util.HashMap;
import java.util.Map;

public class ObjectConverter {

    public static Map<String, Object> CategoryToJson (CategoryModel category, String fields){
        String[] fieldList = fields.split(",");

        Map<String, Object> json = new HashMap<>();
        //Revisar cada campo
        for (String field : fieldList) {
            if (field.trim().isEmpty()) {
                continue; // Salta al siguiente campo
            }

            //Buscar cada campo a que atributo se refiere y agregarlo al json
            switch (field.trim()) {
                case "id":
                    json.put("id", category.getCategoryId());
                    break;
                case "name":
                    json.put("name", category.getName());
                    break;
                case "image":
                    json.put("image", category.getImage());
                    break;
            }
        }

        return json;
    }
}
