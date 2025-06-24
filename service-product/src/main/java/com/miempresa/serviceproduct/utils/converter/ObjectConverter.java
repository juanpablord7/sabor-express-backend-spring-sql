package com.miempresa.serviceproduct.utils.converter;

import com.miempresa.serviceproduct.model.ProductModel;

import java.util.HashMap;
import java.util.Map;

public class ObjectConverter {

    public static Map<String, Object> ProductToJson(ProductModel product, String fields){
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
                    json.put("id", product.getId());
                    break;
                case "name":
                    json.put("name", product.getName());
                    break;
                case "price":
                    json.put("price", product.getPrice());
                    break;
                case "categoryid":
                    json.put("categoryid", product.getCategoryId());
                    break;
                case "category":
                    json.put("category", product.getCategory());
                    break;
                case "image":
                    json.put("image", product.getImage());
                    break;
            }
        }

        return json;
    }

}
