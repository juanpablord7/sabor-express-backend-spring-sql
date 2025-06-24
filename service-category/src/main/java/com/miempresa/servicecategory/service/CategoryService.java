package com.miempresa.servicecategory.service;

import com.miempresa.servicecategory.client.IndexClient;
import com.miempresa.servicecategory.dto.CategoryPatchRequest;
import com.miempresa.servicecategory.dto.CategoryRequest;
import com.miempresa.servicecategory.model.CategoryModel;
import com.miempresa.servicecategory.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(IndexClient indexClient, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryModel> findAllCategory(CategoryRequest filter){

        /*
        //Filtrar por categoria
        if (filter.getName() != null && !filter.getName().isBlank()) {
            query.addCriteria(Criteria.where("name").is(filter.getName()));
        }

        //Filtrar por imagen
        if (filter.getImage() != null && !filter.getImage().isBlank()) {
            query.addCriteria(Criteria.where("image").is(filter.getImage()));
        }
         */

        return categoryRepository.findAll();
    }

    public CategoryModel findCategory(Long id){
        return categoryRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Don't found any Category whit Id: " + id));
    }

    //Create
    public CategoryModel createCategory(CategoryRequest categoryRequest){

        CategoryModel category = CategoryModel.builder()
                .name(categoryRequest.getName())
                .image("category/" + categoryRequest.getImage())
                .build();

        System.out.println("Category to be saved: " + category.toString());

        return categoryRepository.save(category);
    }

    //Replace
    public CategoryModel replaceCategory(Long id, CategoryRequest categoryRequest){
        CategoryModel actualCategory;

        System.out.println("Id of the Product to be replaced: " + id);

        //Obtener actualCategory
        actualCategory = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Don't Found any Category whit that Id: " + id));


        System.out.println("Category to be replaced was found");

        CategoryModel category = CategoryModel.builder()
                .categoryId(actualCategory.getCategoryId())    //Mantener el product_id
                .name(categoryRequest.getName())
                .image("category/"  + categoryRequest.getImage())
                .build();

        System.out.println("Category to replace: " + category.toString());

        return categoryRepository.save(category);
    }

    //Update
    public CategoryModel updateCategory(Long id, CategoryPatchRequest categoryRequest){
        CategoryModel actualCategory;

        System.out.println("ProductId of the Product to be updated: " + id);

        actualCategory = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Don't Found any Product whit that Id: " + id));

        System.out.println("Category to be updated was found");

        if (categoryRequest.getName() != null) {
            actualCategory.setName(categoryRequest.getName());
        }

        //Actualizar el campo image
        if (categoryRequest.getImage() != null){
            actualCategory.setImage("category/" + categoryRequest.getImage());
        }

        System.out.println("Category to update: " + actualCategory.toString());

        return categoryRepository.save(actualCategory);
    }

    public void deleteCategory(Long id){

        categoryRepository.deleteById(id);
    }

}
