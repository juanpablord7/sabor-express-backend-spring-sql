package com.miempresa.serviceproduct.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class ProductModel {

    @Id
    private Long id;

    @Field("name")
    private String name;

    @Indexed
    @Field("category_id")
    private Long categoryId;

    @Field("category")
    private String category;

    @Field("price")
    private Double price;

    @Field("image")
    private String image;
}
