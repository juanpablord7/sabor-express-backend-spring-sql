package com.miempresa.serviceorder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order")
public class OrderModel {
    @Id
    private Long id;

    @Indexed
    @Field("user_id")
    private Long userId;

    @Field("order")
    private List<Long> order;

    @Indexed
    @Field("state")
    private Long state;
}
