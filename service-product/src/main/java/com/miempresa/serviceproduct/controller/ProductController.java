package com.miempresa.serviceproduct.controller;

import com.miempresa.serviceproduct.dto.PaginatedResponse;
import com.miempresa.serviceproduct.dto.ProductPatchRequest;
import com.miempresa.serviceproduct.dto.ProductRequest;
import com.miempresa.serviceproduct.model.Product;
import com.miempresa.serviceproduct.service.ProductService;
import com.miempresa.serviceproduct.utils.converter.ObjectConverter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    //Inyeccion de dependencias:
    private final ProductService productService;

    @Autowired
    //Constructor con inyección de dependencia
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //Get Endpoint
    @GetMapping
    public ResponseEntity<PaginatedResponse<Product>> getProductsByCategory(
                                                        @RequestParam(required = false) Long categoryId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "20") int limit) {

        PaginatedResponse<Product> products = productService.findProductsByCategory(page,limit, categoryId);

        return ResponseEntity.ok(products);
    }

    //Get Single Product By Id
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id,
                                            @RequestParam(required = false) String fields) {

        Product product = productService.findProduct(id);

        //Don't find any product
        if(product.getId() == null){
            return ResponseEntity.ok("");
        }

        //Filtrar por los campos solicitados
        if (fields != null && !fields.isEmpty()) {
            Map<String, Object> response = ObjectConverter.ProductToJson(product, fields);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(product);
    }

    //Get List of Products
    @GetMapping("/find")
    public ResponseEntity<List<Product>> getProducts(@RequestParam String productIds) {
        List<Long> ids = Arrays.stream(productIds.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<Product> products = productService.findListProductById(ids);
        return ResponseEntity.ok(products);
    }

    //Post endpoint
    @PostMapping
    public ResponseEntity<Product> postProduct(@Valid @RequestBody ProductRequest request){
        Product response = productService.createProduct(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Product> putProduct(@PathVariable Long id,
                                              @Valid @RequestBody ProductRequest request){
        Product response = productService.replaceProduct(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Product> patchProduct(@PathVariable Long id,
                                                @Valid @RequestBody ProductPatchRequest request){
        Product response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product Deleted Successfully");
    }


}
