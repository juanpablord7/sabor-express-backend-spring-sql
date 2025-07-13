package com.miempresa.serviceuser.model;

import com.miempresa.serviceuser.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class UserModel {

    @Id
    private Long id;

    @Indexed(unique = true)
    @Field("username")
    private String username;

    @Field("name")
    private String name;

    @Field("password")
    private String password;

    @Field("role")
    private RoleEnum role;
}
