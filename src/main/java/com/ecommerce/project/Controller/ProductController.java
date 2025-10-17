package com.ecommerce.project.Controller;

import com.ecommerce.project.Payload.ProductDTO;
import com.ecommerce.project.Payload.ProductResponse;
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


    //Add all Products API
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

    //Get All Products API
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(){
        ProductResponse productResponse= productService.getAllProducts();
        //call getAllProducts method of productService to get all products from the database
        //productResponse is the response object that contains the list of products
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
        //return response entity with productResponse and HTTP status code 200 (OK)
    }


    //Get all products by category API
    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId){
        //PathVariable annotation is used to bind the categoryId path variable to the categoryId parameter
        //call getProductsByCategory method of productService to get products by category from the database
        //ProductResponse is the response object that contains the list of products
        ProductResponse productResponse=productService.getProductsByCategory(categoryId);
        //productService.getProductsByCategory(categoryId) fetches products for the given categoryId
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
        //return response entity with productResponse and HTTP status code 200 (OK)
    }
}
