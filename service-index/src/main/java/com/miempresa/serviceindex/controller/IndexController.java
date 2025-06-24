package com.miempresa.serviceindex.controller;

import com.miempresa.serviceindex.dto.IndexFilter;
import com.miempresa.serviceindex.dto.IndexRequest;
import com.miempresa.serviceindex.model.IndexModel;
import com.miempresa.serviceindex.service.IndexService;
import com.miempresa.serviceindex.utils.converter.ObjectConverter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class IndexController {
    private final IndexService indexService;

    @Autowired
    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    //Endpoint: GET /index & /index?nameIndex=name&index=22
    @GetMapping()
    public ResponseEntity<List<IndexModel>> getAllIndex(@Valid IndexFilter filter) {
        List<IndexModel> indexes = indexService.findAllIndex(filter);
        return ResponseEntity.ok(indexes);
    }

    //Endpoint: GET /index/{nameIndex} & /index/{nameIndex}?fields=nameIndex,sequenceValue
    @GetMapping(path = "/{nameIndex}")
    public ResponseEntity<?> getIndex(@PathVariable String nameIndex,
                                      @RequestParam(required = false) String fields) {

        IndexModel index = indexService.findIndex(nameIndex);

        //Filtrar por los campos solicitados
        if (fields != null && !fields.isEmpty()) {
            Map<String, Object> response = ObjectConverter.IndexToJson(index, fields);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(index);
    }

    //Endpoint: POST /index/{nameIndex} & /index/{nameIndex}?fields=nameIndex,sequenceValue
    @PostMapping(path = "/{nameIndex}")
    public ResponseEntity<Object> postIndex(@PathVariable String nameIndex,
                                            @RequestParam(required = false) String fields) {

        if(nameIndex == null || nameIndex.isEmpty()) {
            throw new IllegalArgumentException("No nameIndex was provided");
        }
        IndexModel index = indexService.createAndUpdateIndex(nameIndex);

        //Filtrar por los campos solicitados
        if (fields != null && !fields.isEmpty()) {
            //Retornar los campos solicitados
            Map<String, Object> response = ObjectConverter.IndexToJson(index, fields);
            return ResponseEntity.ok(response);
        }else{
            //Retornar solo el número (Long)
            return ResponseEntity.status(201).body(index.getSequenceValue());
        }
    }

    //Endpoint: PUT /index/{nameIndex}
    @PutMapping(path = "/{nameIndex}")
    public ResponseEntity<IndexModel> putIndex(@PathVariable String nameIndex,
                                               @RequestBody IndexRequest request) {

        IndexModel index = indexService.updateIndex(nameIndex, request);

        return ResponseEntity.ok(index);
    }

    //Endpoint: DELETE /index/{nameIndex}
    @DeleteMapping(path = "/{nameIndex}")
    public ResponseEntity<String> deleteIndex(@PathVariable String nameIndex){
        indexService.deleteIndex(nameIndex);
        return ResponseEntity.ok("Index Deleted Successfully");
    }
}