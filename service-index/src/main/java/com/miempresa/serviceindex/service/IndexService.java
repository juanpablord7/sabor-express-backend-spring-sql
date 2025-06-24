package com.miempresa.serviceindex.service;

import com.miempresa.serviceindex.dto.IndexFilter;
import com.miempresa.serviceindex.dto.IndexRequest;
import com.miempresa.serviceindex.model.IndexModel;
import com.miempresa.serviceindex.repository.IndexCrudRepository;
import com.miempresa.serviceindex.repository.IndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;;


@Service
public class IndexService {

    //Dependencia del repositorio de index
    private final IndexCrudRepository indexCrudRepository;
    private final IndexRepository indexRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public IndexService(IndexCrudRepository indexCrudRepository, IndexRepository indexRepository, MongoTemplate mongoTemplate) {
        this.indexCrudRepository = indexCrudRepository;
        this.indexRepository = indexRepository;
        this.mongoTemplate = mongoTemplate;
    }

    //Get
    public List<IndexModel> findAllIndex(IndexFilter filter) {
        Query query = new Query();

        //Filtrar por categoria
        if (filter.getNameIndex() != null && !filter.getNameIndex().isEmpty()) {
            query.addCriteria(Criteria.where("name_index").is(filter.getNameIndex()));
        }

        //Filtrar por imagen
        if (filter.getSequenceValue() != null) {
            query.addCriteria(Criteria.where("sequenceValue").is(filter.getSequenceValue()));
        }

        return mongoTemplate.find(query, IndexModel.class);
    }

    //Get by id
    public IndexModel findIndexById(String id) {
        return indexCrudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Don't Found any Index whit Id: " + id));

    }

    //Get by IndexName
    public IndexModel findIndex(String nameIndex) {
        return indexCrudRepository.findByNameIndex(nameIndex)
                .orElseThrow(() -> new IllegalArgumentException("Don't Found any Index whit index_name: " + nameIndex));

    }

    // Create and Update the Index (Default)
    public IndexModel createAndUpdateIndex(String nameIndex) {
        IndexModel index = indexRepository.ReadAndUpdateIndex(nameIndex);

        if (index == null) {
            throw new IllegalStateException("No se encontró la secuencia para: " + nameIndex);
        }

        return index;
    }

    //Update
    public IndexModel updateIndex(String nameIndex, IndexRequest indexRequest) {
        IndexModel actualIndex;

        System.out.println("Name of the index to be updated: " + nameIndex);

        actualIndex = indexCrudRepository.findByNameIndex(nameIndex)
                .orElseThrow(() ->
                        new IllegalArgumentException("Don't Found any Index whit that Id: " + nameIndex));

        System.out.println("Index to be replace was Found");

        if(indexRequest.getNameIndex() != null){
            actualIndex.setNameIndex(indexRequest.getNameIndex());
        }

        if(indexRequest.getSequenceValue() >= actualIndex.getSequenceValue()){
            actualIndex.setSequenceValue(indexRequest.getSequenceValue());
        }

        System.out.println("Index to replace: " + actualIndex.toString());

        return indexCrudRepository.save(actualIndex);
    }

    //Delete
    public void deleteIndex(String nameIndex) {
        IndexModel index = findIndex(nameIndex);
        indexCrudRepository.deleteById(index.getId());
    }

}