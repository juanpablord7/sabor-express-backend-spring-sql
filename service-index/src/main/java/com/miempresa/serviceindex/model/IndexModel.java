package com.miempresa.serviceindex.model;

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
@Document(collection = "index")
public class IndexModel {

    @Id
    private String id;

    @Field("name_index")
    @Indexed(unique = true)
    private String nameIndex;

    @Field("sequence_value")
    private Long sequenceValue;
}
