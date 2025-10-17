package com.ecommerce.project.Controller;

import com.ecommerce.project.Payload.ProductDTO;
import com.ecommerce.project.Service.ProductService;
import com.ecommerce.project.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
            //Autowired annotation is used for automatic dependency injection.
    ProductService productService;
    //ProductService is a service class that contains business logic related to products.


    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product,
                                                 @PathVariable Long categoryId){
        //RequestBody annotation is used to bind the request body to the product object.
        //PathVariable annotation is used to bind the categoryId path variable to the categoryId parameter
        ProductDTO productDTO= productService.addProduct(categoryId, product);
        //call addProduct method of productService to add product to the database
        //productDTO is the response object that contains the product details
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
        //return response entity with productDTO and HTTP status code 201 (CREATED)

    }
}
