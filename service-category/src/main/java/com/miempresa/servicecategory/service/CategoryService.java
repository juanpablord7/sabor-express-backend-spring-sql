package com.miempresa.servicecategory.service;

import com.miempresa.servicecategory.dto.CategoryPatchRequest;
import com.miempresa.servicecategory.dto.CategoryRequest;
import com.miempresa.servicecategory.model.Category;
import com.miempresa.servicecategory.repository.CategoryRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final Validator validator;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, Validator validator) {
        this.categoryRepository = categoryRepository;
        this.validator = validator;
    }

    public Category save(Category category){
        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }

        return categoryRepository.save(category);
    }

    public List<Category> findAllCategory(){
        return categoryRepository.findAll(Sort.by("id"));
    }

    public Category findCategory(Long id){
        return categoryRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Don't found any Category whit Id: " + id));
    }

    //Create
    public Category createCategory(CategoryRequest categoryRequest){

        Category category = Category.builder()
                .name(categoryRequest.getName())
                .image(categoryRequest.getImage())
                .build();

        System.out.println("Category to be saved: " + category.toString());

        return save(category);
    }

    //Replace
    public Category replaceCategory(Long id, CategoryRequest categoryRequest){
        Category actualCategory;

        System.out.println("Id of the Product to be replaced: " + id);

        //Obtener actualCategory
        actualCategory = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Don't Found any Category whit that Id: " + id));


        System.out.println("Category to be replaced was found");

        Category category = Category.builder()
                .id(actualCategory.getId())    //Mantener el product_id
                .name(categoryRequest.getName())
                .image(categoryRequest.getImage())
                .build();

        System.out.println("Category to replace: " + category.toString());

        return save(category);
    }

    //Update
    public Category updateCategory(Long id, CategoryPatchRequest categoryRequest){
        Category actualCategory;

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
            actualCategory.setImage(categoryRequest.getImage());
        }

        System.out.println("Category to update: " + actualCategory.toString());

        return save(actualCategory);
    }

    public void deleteCategory(Long id){

        categoryRepository.deleteById(id);
    }

}
