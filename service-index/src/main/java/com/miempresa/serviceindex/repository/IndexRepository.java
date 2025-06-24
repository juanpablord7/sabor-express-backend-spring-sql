package com.miempresa.serviceindex.repository;

import com.miempresa.serviceindex.model.IndexModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class IndexRepository {

    private final MongoOperations mongoOperations;

    @Autowired
    public IndexRepository(MongoOperations mongoOperations){
        this.mongoOperations = mongoOperations;
    }


    public IndexModel ReadAndUpdateIndex(String nameIndex) {
        Query query = new Query(Criteria.where("name_index").is(nameIndex));
        Update update = new Update()
                .set("name_index", nameIndex)
                .inc("sequence_value", 1);
        FindAndModifyOptions options = FindAndModifyOptions.options()
                .returnNew(true)
                .upsert(true);

        return mongoOperations.findAndModify(query, update, options, IndexModel.class);
    }


}
