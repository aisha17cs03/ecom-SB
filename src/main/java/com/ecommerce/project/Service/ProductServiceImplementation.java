package com.ecommerce.project.Service;

import com.ecommerce.project.Payload.ProductDTO;
import com.ecommerce.project.Payload.ProductResponse;
import com.ecommerce.project.Repositories.CategoryRepository;
import com.ecommerce.project.Repositories.ProductRepository;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {
        //product and categoryId from controller
        //fetch category from database
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        //set category to product
        //categoryId find category object from database
        //fetch category object from database
        //ResourceNotFoundException is a custom exception class that is thrown when a resource is not found in the database
        product.setImage("default.png");
        //set default image to product
        product.setCategory(category);
        //set category to product
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        //calculate special price and set it to product
        //specialPrice is calculated by subtracting the discount from the price
        product.setSpecialPrice(specialPrice);
        //set special price to product
        Product savedProduct = productRepository.save(product);
        //save product to database
        return modelMapper.map(savedProduct, ProductDTO.class);
        //convert product entity to product DTO and return it
    }

    @Override
    public ProductResponse getAllProducts() {
        //get all products from database
        List<Product> products = productRepository.findAll();
        //convert list of product entities to list of product DTOs
        List<ProductDTO> productDTOS = products.stream().
                //products.stream() creates a stream of products
                //products stream to map each product to productDTO
                        map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        //convert each product entity to product DTO using modelMapper
        //create ProductResponse object
        //ProductResponse is a custom class that contains list of product DTOs and other pagination details
        //ProductDTO is a Data Transfer Object that contains product details
        ProductResponse productResponse = new ProductResponse();
        //ProductResponse object to set list of product DTOs
        //ProductResponse object to return to controller
        productResponse.setContent(productDTOS);
        //set list of product DTOs to ProductResponse object
        return productResponse;
        //return ProductResponse object to controller
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId) {
        //get category from database
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        //fetch products by category from database
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        //convert list of product entities to list of product DTOs
        List<ProductDTO> productDTOS = products.stream().
                map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        //create ProductResponse object
        //ProductResponse is a custom class that contains list of product DTOs and other pagination details
        ProductResponse productResponse = new ProductResponse();
        //ProductResponse object to set list of product DTOs
        productResponse.setContent(productDTOS);
        //set list of product DTOs to ProductResponse object
        return productResponse;
        //return ProductResponse object to controller
        //ProductDTO is a Data Transfer Object that contains product details
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        //search products by keyword from database
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        //convert list of product entities to list of product DTOs
        //findByProductNameLikeIgnoreCase is a custom method in ProductRepository that searches products by name ignoring case
        List<ProductDTO> productDTOS = products.stream().
                map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        //create ProductResponse object
        //ProductResponse is a custom class that contains list of product DTOs and other pagination details
        //ProductDTO is a Data Transfer Object that contains product details
        ProductResponse productResponse = new ProductResponse();
        //ProductResponse object to set list of product DTOs
        productResponse.setContent(productDTOS);
        //set list of product DTOs to ProductResponse object
        //setContent method sets the list of product DTOs to the ProductResponse object
        return productResponse;
        //return ProductResponse object to controller
        //ProductResponse object contains the list of product DTOs matching the keyword
        //If no products are found, an empty ProductResponse object is returned
        //The controller will handle the response accordingly
    }

    @Override
    public ProductDTO upadateProduct(Long productId, Product product) {
        //get existing product from database
        Product productFromDb=productRepository.findById(productId).
                orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));

        //update product information with in request body
        //product object contains updated product details from controller
        //set updated values to existing product
        //getProductName, getDescription, getQuantity, getPrice, getDiscount, getSpecialPrice methods are used to get updated values from product object
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setSpecialPrice(product.getSpecialPrice());

        //save updated product to database
        Product sacedProduct=productRepository.save(productFromDb);
        //convert updated product entity to product DTO and return it
        return modelMapper.map(sacedProduct, ProductDTO.class);
        //map updated product entity to product DTO using modelMapper
        //return product DTO to controller

    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        //delete product from database
        Product product=productRepository.findById(productId).
                //find product by productId
                orElseThrow(()->new ResourceNotFoundException("Product", "productId", productId));
        //if product not found, throw ResourceNotFoundException
        //product object contains product details to be deleted
        productRepository.delete(product);
        //delete product from database
        return modelMapper.map(product, ProductDTO.class);
        //convert deleted product entity to product DTO and return it
    }
}
