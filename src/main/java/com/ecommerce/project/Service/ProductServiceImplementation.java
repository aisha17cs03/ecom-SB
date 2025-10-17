package com.ecommerce.project.Service;

import com.ecommerce.project.Payload.ProductDTO;
import com.ecommerce.project.Repositories.CategoryRepository;
import com.ecommerce.project.Repositories.ProductRepository;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImplementation implements ProductService{

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
        Category category =categoryRepository.findById(categoryId)
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
}
