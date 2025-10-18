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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        //product and categoryId from controller
        //fetch category from database
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        //set category to product
        //categoryId find category object from database
        //fetch category object from database
        //ResourceNotFoundException is a custom exception class that is thrown when a resource is not found in the database
        Product product = modelMapper.map(productDTO, Product.class);
        //convert product DTO to product entity
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
    public ProductDTO upadateProduct(Long productId, ProductDTO productDTO) {
        //get existing product from database
        Product productFromDb=productRepository.findById(productId).
                orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));

        Product product=modelMapper.map(productDTO, Product.class);
        //convert product DTO to product entity

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

    @Override
    public ProductDTO upadateProductImage(Long productId, MultipartFile image) throws IOException {
        //update product image in database
        //Get product from database
        Product productFromDb=productRepository.findById(productId).
                orElseThrow(()->new ResourceNotFoundException("Product", "productId", productId));
        //product object contains product details to be updated
        //productId to find product from database

        //upload image to server
        //Get the file name of the uploaded image
        String path="images/";
        String fileName=uploadImage(path, image);
        //path is the directory path where the image will be uploaded
        //uploadImage method uploads the image to the server and returns the file name


        //Updating new file name to product
        productFromDb.setImage(fileName);
        //set new file name to product object

        Product updatedProduct=productRepository.save(productFromDb);
        //Save updated product to database
        //set new file name to product object

        //return DTO after mapping product to DTO
        return modelMapper.map(updatedProduct, ProductDTO.class);
        //convert updated product entity to product DTO and return it
        //map updated product entity to product DTO using modelMapper
        //return product DTO to controller

    }

    private String uploadImage(String path, MultipartFile file) throws IOException {
        //Logic to upload image to server


        //File name of current/original image
        String originalFileName=file.getOriginalFilename();
        //Extract file extension from original file name


        //Generate unique file name using current time in milliseconds
        String randomId= UUID.randomUUID().toString();
        //Create the complete file path by combining path, randomId, and file extension
        //mat.jpg--->1234-->1234.jpg
        String fileName=randomId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        //complete file name with path
        String filePath=path + File.separator + fileName;
        //Create a File object for the destination path

        //check if path exists, if not create
        File floder=new File(path);
        if(!floder.exists()) {
            floder.mkdir();
            //create directory if not exists
            //mkdir() method creates the directory
        }
        //Upload the file to the server
        Files.copy(file.getInputStream(), Paths.get(filePath));
        //Files.copy() method copies the file to the destination path

        //Return the file name
        return fileName;
        //return the file name to the caller
    }
}
