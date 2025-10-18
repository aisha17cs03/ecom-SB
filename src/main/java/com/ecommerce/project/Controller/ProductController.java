package com.ecommerce.project.Controller;

import com.ecommerce.project.Payload.ProductDTO;
import com.ecommerce.project.Payload.ProductResponse;
import com.ecommerce.project.Service.ProductService;
//import com.ecommerce.project.model.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
            //Autowired annotation is used for automatic dependency injection.
    ProductService productService;
    //ProductService is a service class that contains business logic related to products.


    //Add all Products API
    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO,
                                                 @PathVariable Long categoryId){
        //Valid annotation is used to validate the product object based on the constraints defined in the Product class.
        //RequestBody annotation is used to bind the request body to the product object.
        //PathVariable annotation is used to bind the categoryId path variable to the categoryId parameter
//        ProductDTO productDTO= productService.addProduct(categoryId, product);
        ProductDTO savedProductDTO=productService.addProduct(categoryId, productDTO);
        //savedProductDTO is the response object that contains the product details after saving to the database
        //call addProduct method of productService to add product to the database
        //productDTO is the response object that contains the product details
//        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
        //savedProductDTO contains the saved product details
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


    //Get all products by keyword api
    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword){
        //PathVariable annotation is used to bind the keyword path variable to the keyword parameter
        //call searchProductByKeyword method of productService to get products by keyword from the database
        //keyword is a string that is used to search products
        ProductResponse productResponse=productService.searchProductByKeyword(keyword);
        //ProductResponse is the response object that contains the list of products
        //productService.searchProductByKeyword(keyword) fetches products matching the given keyword
        //searchProductByKeyword is a method in ProductService that searches products based on the keyword
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
        //return response entity with productResponse and HTTP status code 302 (FOUND)
        //http status code 302 indicates that the requested resource has been found
    }


    //Update product API
    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO,
                                                    @PathVariable Long productId){
        //Valid annotation is used to validate the product object based on the constraints defined in the Product class.
        //RequestBody annotation is used to bind the request body to the product object.
        //PathVariable annotation is used to bind the productId path variable to the productId parameter
        //product object contains the updated product details
//        ProductDTO updatedProductDTO=productService.upadateProduct(productId, product);
        ProductDTO updatedProductDTO=productService.upadateProduct(productId, productDTO);
        //call updateProduct method of productService to update product in the database
        //updateProduct method takes product object and productId as parameters
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
        //return response entity with updatedProductDTO and HTTP status code 200 (OK)
    }


    //Delete product API
    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        //PathVariable annotation is used to bind the productId path variable to the productId parameter
        //deleteProduct method of productService to delete product from the database
        ProductDTO deleteProduct=productService.deleteProduct(productId);
        //deleteProduct method takes productId as parameter
        //deleteProduct is the response object that contains the details of the deleted product
        return new ResponseEntity<>(deleteProduct, HttpStatus.OK);
        //return response entity with deleteProduct and HTTP status code 200 (OK)
    }

    //Update Product image API
    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO updatedProduct=productService.upadateProductImage(productId, image);
        //updateProductImage method of productService to update product image in the database
        //updateProductImage method takes productId and image as parameters
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        //return response entity with updatedProduct and HTTP status code 200 (OK)
    }
}
