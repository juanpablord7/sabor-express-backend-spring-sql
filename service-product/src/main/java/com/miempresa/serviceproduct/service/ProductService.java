package com.miempresa.serviceproduct.service;

import com.miempresa.serviceproduct.dto.PaginatedResponse;
import com.miempresa.serviceproduct.dto.ProductPatchRequest;
import com.miempresa.serviceproduct.dto.ProductRequest;
import com.miempresa.serviceproduct.model.Product;
import com.miempresa.serviceproduct.repository.ProductRepository;
import com.miempresa.serviceproduct.utils.sanitizer.StringSanitizer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final StringSanitizer stringSanitizer;
    private final Validator validator;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          StringSanitizer stringSanitizer, Validator validator) {
        this.productRepository = productRepository;
        this.stringSanitizer = stringSanitizer;
        this.validator = validator;
    }

    //Validate and save the product
    public Product save(Product product){
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }

        return productRepository.save(product);
    }

    // Get All
    public PaginatedResponse<Product> findProductsByCategory(Integer page, Integer limit, Long categoryId) {
        Pageable pageable = PageRequest.of(page, limit);

        Page<Product> productsPage;

        if (categoryId == null) {
            productsPage = productRepository.findAll(pageable);
        } else {
            productsPage = productRepository.findAllByCategory(categoryId, pageable);
        }

        return new PaginatedResponse<>(
                productsPage.getContent(),
                productsPage.getNumber(),
                productsPage.getTotalPages(),
                productsPage.getTotalElements(),
                productsPage.getSize()
        );
    }

    public Product findProduct(Long id){
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Don't Found any Product whit that Id: " + id));

    }

    public List<Product> findListProductById(List<Long> ids) {
        return productRepository.findAllByIdIn(ids);
    }

    public Product createProduct(ProductRequest request){
        System.out.println("Request product to be saved: " + request.toString());

        Long category = 0L;
        if(request.getCategory() != null){
            category = request.getCategory();
        }

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .category(category)
                .image(stringSanitizer.sanitizeXSS(request.getImage()))
                .build();

        System.out.println("Product to save: " + product.toString());

        return save(product);
    }

    //Replace
    public Product replaceProduct(Long id, ProductRequest request){
        Product actualProduct;

        if(id == null){
            throw new IllegalArgumentException("Id was not provided");
        }

        System.out.println("Id of the Product to be updated: " + id);

        actualProduct= productRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Don't Found any Product whit the Id: " + id));


        System.out.println("Product to be replace was Found");


        Long category = 0L;
        if(request.getCategory() != null){
            category = request.getCategory();
        }

        Product product = Product.builder()
                .id(actualProduct.getId())  //Mantener el id
                .name(request.getName())
                .price(request.getPrice())
                .category(category)
                .image(stringSanitizer.sanitizeXSS(request.getImage()))
                .build();

        System.out.println("Product to replace: " + product.toString());

        return save(product);
    }

    //Update
    public Product updateProduct(Long id, ProductPatchRequest request){

        Product actualProduct;

        if(id  != null){
            System.out.println("Id of the Product to be updated: " + id);

            actualProduct= productRepository.findById(id)
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
            actualProduct.setImage(stringSanitizer.sanitizeXSS(request.getImage()));
        }

        if(request.getCategory() != null){
            actualProduct.setCategory(request.getCategory());
        }else{
            actualProduct.setCategory(0L);
        }

        System.out.println("Product to update: " + actualProduct.toString());

        return save(actualProduct);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }


}
