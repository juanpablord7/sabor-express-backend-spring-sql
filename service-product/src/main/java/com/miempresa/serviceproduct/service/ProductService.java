package com.miempresa.serviceproduct.service;

import com.miempresa.serviceproduct.client.CategoryClient;
import com.miempresa.serviceproduct.client.IndexClient;
import com.miempresa.serviceproduct.dto.PaginatedResponse;
import com.miempresa.serviceproduct.dto.ProductPatchRequest;
import com.miempresa.serviceproduct.dto.ProductRequest;
import com.miempresa.serviceproduct.model.ProductModel;
import com.miempresa.serviceproduct.repository.ProductCrudRepository;
import com.miempresa.serviceproduct.utils.sanitizer.StringSanitizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductCrudRepository productCrudRepository;
    private final StringSanitizer stringSanitizer;

    //Microservicios
    private final IndexClient indexClient;
    private final CategoryClient categoryClient;

    @Autowired
    public ProductService(ProductCrudRepository productCrudRepository,
                          StringSanitizer stringSanitizer,
                          IndexClient indexClient,
                          CategoryClient categoryClient) {
        this.productCrudRepository = productCrudRepository;
        this.stringSanitizer = stringSanitizer;
        this.indexClient = indexClient;
        this.categoryClient = categoryClient;
    }


    // Get All
    public PaginatedResponse<ProductModel> findProductsByCategory(Integer page, Integer limit, Long categoryId) {
        Pageable pageable = PageRequest.of(page, limit);

        Page<ProductModel> productsPage;

        if (categoryId == null) {
            productsPage = productCrudRepository.findAll(pageable);
        } else {
            productsPage = productCrudRepository.findAllByCategoryId(categoryId, pageable);
        }

        return new PaginatedResponse<>(
                productsPage.getContent(),
                productsPage.getNumber(),
                productsPage.getTotalPages(),
                productsPage.getTotalElements(),
                productsPage.getSize()
        );
    }

    public ProductModel findProduct(Long id){
        return productCrudRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Don't Found any Product whit that Id: " + id));

    }

    public List<ProductModel> findProductsByProductId(List<Long> ids) {
        return productCrudRepository.findAllByIdIn(ids);
    }

    public ProductModel createProduct(ProductRequest request){
        System.out.println("Request product to be saved: " + request.toString());

        //Category information
        Long categoryId = 0L;
        String category = "";

        //Obtener el nombre y id de la categoría
        if(request.getCategoryId() != null){
            String categoryResponse = categoryClient.getCategory(request.getCategoryId());
            //Si se obtuvo la categoria asignarla
            if(categoryResponse != null){
                categoryId = request.getCategoryId();
                category= categoryResponse;
            }
        }

        ProductModel product = ProductModel.builder()
                .name(request.getName())
                .price(request.getPrice())
                .categoryId(categoryId)
                .category(category)
                .image("products/" + stringSanitizer.sanitizeXSS(request.getImage()))
                .build();

        product.setId(indexClient.getIndex());

        System.out.println("Product to save: " + product.toString());

        return productCrudRepository.save(product);
    }

    //Replace
    public ProductModel replaceProduct(Long id, ProductRequest request){
        ProductModel actualProduct;

        if(id == null){
            throw new IllegalArgumentException("Id was not provided");
        }

        System.out.println("Id of the Product to be updated: " + id);

        actualProduct= productCrudRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Don't Found any Product whit the Id: " + id));


        System.out.println("Product to be replace was Found");

        //Category information
        Long categoryId = 0L;
        String category = "";

        //Obtener el nombre y id de la categoría
        if(request.getCategoryId() != null){
            String categoryResponse = categoryClient.getCategory(request.getCategoryId());
            //Si se obtuvo la categoria asignarla
            if(categoryResponse != null){
                categoryId = request.getCategoryId();
                category= categoryResponse;
            }
        }

        ProductModel product = ProductModel.builder()
                .id(actualProduct.getId())  //Mantener el id
                .name(request.getName())
                .price(request.getPrice())
                .categoryId(categoryId)
                .category(category)
                .image("products/" + stringSanitizer.sanitizeXSS(request.getImage()))
                .build();

        System.out.println("Product to replace: " + product.toString());

        return productCrudRepository.save(product);
    }

    //Update
    public ProductModel updateProduct(Long id, ProductPatchRequest request){

        ProductModel actualProduct;

        if(id  != null){
            System.out.println("Id of the Product to be updated: " + id);

            actualProduct= productCrudRepository.findById(id)
                    .orElseThrow(() ->
                            new IllegalArgumentException("Don't Found any Product whit the Id: " + id));
        } else {
            throw new IllegalArgumentException("Id was not provided");
        }

        System.out.println("Product to be Update was Found");

        if (request.getName() != null) {
            actualProduct.setName(request.getName());
        }

        if(request.getPrice() != null){
            actualProduct.setPrice(request.getPrice());
        }
        if(request.getImage() != null){
            actualProduct.setImage("products/" + stringSanitizer.sanitizeXSS(request.getImage()));
        }

        //Category information
        if(request.getCategoryId() != null){
            Long categoryId = 0L;
            String category = "";
            //Obtener el nombre y id de la categoría
            String categoryResponse = categoryClient.getCategory(request.getCategoryId());
            //Si se obtuvo la categoria asignarla
            if(categoryResponse != null){
                categoryId = request.getCategoryId();
                category= categoryResponse;
            }

            actualProduct.setCategoryId(categoryId);
            actualProduct.setCategory(category);
        }

        System.out.println("Product to update: " + actualProduct.toString());

        return productCrudRepository.save(actualProduct);
    }

    public void deleteProduct(Long id){
        productCrudRepository.deleteById(id);
    }


}
