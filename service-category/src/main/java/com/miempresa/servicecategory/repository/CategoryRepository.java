package com.miempresa.servicecategory.repository;

import com.miempresa.servicecategory.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

}

