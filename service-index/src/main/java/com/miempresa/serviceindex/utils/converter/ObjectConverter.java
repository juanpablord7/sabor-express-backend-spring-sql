package com.miempresa.serviceindex.utils.converter;

import com.miempresa.serviceindex.model.IndexModel;

import java.util.HashMap;
import java.util.Map;

public class ObjectConverter {

    public static Map<String, Object> IndexToJson(IndexModel index, String fields){
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
                    json.put("id", index.getId());
                    break;
                case "nameIndex":
                    json.put("nameIndex", index.getNameIndex());
                    break;
                case "sequenceValue":
                    json.put("sequenceValue", index.getSequenceValue());
                    break;
            }
        }

        return json;
    }
}
