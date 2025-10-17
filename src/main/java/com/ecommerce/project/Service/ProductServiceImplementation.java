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

    @Override
    public ProductResponse getAllProducts() {
        //get all products from database
        List<Product> products = productRepository.findAll();
        //convert list of product entities to list of product DTOs
        List<ProductDTO> productDTOS=products.stream().
                //products.stream() creates a stream of products
                //products stream to map each product to productDTO
                map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        //convert each product entity to product DTO using modelMapper
        //create ProductResponse object
        //ProductResponse is a custom class that contains list of product DTOs and other pagination details
        //ProductDTO is a Data Transfer Object that contains product details
        ProductResponse productResponse=new ProductResponse();
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
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        //fetch products by category from database
        List<Product> products=productRepository.findByCategoryOrderByPriceAsc(category);
        //convert list of product entities to list of product DTOs
        List<ProductDTO> productDTOS=products.stream().
                map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        //create ProductResponse object
        //ProductResponse is a custom class that contains list of product DTOs and other pagination details
        ProductResponse productResponse=new ProductResponse();
        //ProductResponse object to set list of product DTOs
        productResponse.setContent(productDTOS);
        //set list of product DTOs to ProductResponse object
        return productResponse;
        //return ProductResponse object to controller
        //ProductDTO is a Data Transfer Object that contains product details
    }
}
