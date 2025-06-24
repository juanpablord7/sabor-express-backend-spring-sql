package com.miempresa.serviceindex.repository;

import com.miempresa.serviceindex.model.IndexModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IndexCrudRepository extends MongoRepository<IndexModel, String> {

    //Agregar método para encontrar por index_name
    Optional<IndexModel> findByNameIndex(String nameIndex);
}
