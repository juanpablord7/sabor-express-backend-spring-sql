package com.miempresa.servicecategory.controller;

import com.miempresa.servicecategory.dto.CategoryPatchRequest;
import com.miempresa.servicecategory.dto.CategoryRequest;
import com.miempresa.servicecategory.model.Category;
import com.miempresa.servicecategory.service.CategoryService;
import com.miempresa.servicecategory.utils.converter.ObjectConverter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //Endpoint: GET /api/category & /api/category?name=pizzas api/category?name=pizzas&image=img.png
    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategory(){
        return ResponseEntity.ok(categoryService.findAllCategory());
    }

    //Endpoint: GET /api/category/{categoryId} & /api/category/{categoryId}?lang=es
    // & /api/category/{categoryId}?fields=categoryId,category
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id,
                                         @RequestParam(required = false) String fields){

        Category category = categoryService.findCategory(id);

        //Don't find any category
        if(category.getId() == null){
            return ResponseEntity.ok("");
        }

        //Filtrar por los campos solicitados
        if (fields != null && !fields.isEmpty()) {
            Map<String, Object> response = ObjectConverter.CategoryToJson(category, fields);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(category);

    }

    //Endpoint: POST /api/category
    @PostMapping
    public ResponseEntity<Category> postCategory(@Valid @RequestBody CategoryRequest request){
        Category category = categoryService.createCategory(request);
        return ResponseEntity.status(201).body(category);
    }

    //Endpoint: PUT /api/category/123
    @PutMapping(path = "/{id}")
    public ResponseEntity<Category> putCategory(@PathVariable Long id,
                                                @Valid @RequestBody CategoryRequest request){
        Category category = categoryService.replaceCategory(id, request);
        return ResponseEntity.ok(category);
    }

    //Endpoint: PATCH /api/category/123
    @PatchMapping(path = "/{id}")
    public ResponseEntity<Category> patchCategory(@PathVariable Long id,
                                                  @Valid @RequestBody CategoryPatchRequest request){
        Category category = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(category);
    }

    //Endpoint: DELETE /api/category/123
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category Deleted Successfully");
    }
}
