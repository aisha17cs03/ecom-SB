package com.ecommerce.project.Service;

import com.ecommerce.project.Payload.ProductDTO;
import com.ecommerce.project.Payload.ProductResponse;
import com.ecommerce.project.Repositories.CategoryRepository;
import com.ecommerce.project.Repositories.ProductRepository;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    //injecting value from application.properties file
    //refer to application.properties file for more details
    private String path;


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

        boolean isProductNotPresent=true;
        //check if product already exists in the category
        List<Product> products=category.getProducts();
        //get list of products from category
        for(Product value : products){//iterate through list of products
            if(value.getProductName().equalsIgnoreCase(productDTO.getProductName())){//check if product name already exists in the category equalsIgnoreCase is used to ignore case sensitivity
                isProductNotPresent=false;//set isProductNotPresent to false if product name already exists
                break;
            }
        }
        if(isProductNotPresent) {//check if product already exists in the category
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
        }else{
            throw new APIException("Product already exists!!!");
        }//if product already exists, throw APIException
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        //get all products from database
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        //create Sort object based on sortBy and sortOrder parameters

        Pageable pageDetails= PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        //create Pageable object based on pageNumber, pageSize and sortByAndOrder parameters

        Page<Product> pageProducts=productRepository.findAll(pageDetails);
        //fetch paginated products from database

        List<Product> products=pageProducts.getContent();
        //get list of products from Page object

//        List<Product> products = productRepository.findAll();
        //convert list of product entities to list of product DTOs
        List<ProductDTO> productDTOS = products.stream().
                //products.stream() creates a stream of products
                //products stream to map each product to productDTO
                        map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        //convert each product entity to product DTO using modelMapper
        //create ProductResponse object
        //ProductResponse is a custom class that contains list of product DTOs and other pagination details
        //ProductDTO is a Data Transfer Object that contains product details

//        if(products.isEmpty()){
//            throw new APIException("No products Exists!!!");
//        }
        //commenting validation for no products found if required can be uncommented
        //if no products found, throw APIException

        ProductResponse productResponse = new ProductResponse();
        //ProductResponse object to set list of product DTOs
        //ProductResponse object to return to controller
        productResponse.setContent(productDTOS);
        //set list of product DTOs to ProductResponse object

        productResponse.setPageNumber(pageProducts.getNumber());
        //set current page number to ProductResponse object
        productResponse.setPageSize(pageProducts.getSize());
        //set page size to ProductResponse object
        productResponse.setTotalElements(pageProducts.getTotalElements());
        //set total number of elements to ProductResponse object
        productResponse.setTotalPages(pageProducts.getTotalPages());
        //set total number of pages to ProductResponse object
        productResponse.setLastPage(pageProducts.isLast());
        //set whether it is the last page or not to ProductResponse object

        return productResponse;
        //return ProductResponse object to controller
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        //get category from database
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        //fetch products by category from database

        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        //create Sort object based on sortBy and sortOrder parameters

        Pageable pageDetails= PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        //create Pageable object based on pageNumber, pageSize and sortByAndOrder parameters

        Page<Product> pageProducts=productRepository.findByCategoryOrderByPriceAsc(category, pageDetails);
        //fetch paginated products from database

        List<Product> products=pageProducts.getContent();
        //get list of products from Page object

        //if u want to add validation for product size is 0
        //Adding validation for no products found in category
        if(products.isEmpty()){
            throw new APIException(category.getCategoryName()+" category has no products!!!");
        }//if no products found in category, throw APIException category has no products


        //convert list of product entities to list of product DTOs
        List<ProductDTO> productDTOS = products.stream().
                map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        //create ProductResponse object
        //ProductResponse is a custom class that contains list of product DTOs and other pagination details
        ProductResponse productResponse = new ProductResponse();
        //ProductResponse object to set list of product DTOs
        productResponse.setContent(productDTOS);
        //set list of product DTOs to ProductResponse object

        productResponse.setPageNumber(pageProducts.getNumber());
        //set current page number to ProductResponse object
        productResponse.setPageSize(pageProducts.getSize());
        //set page size to ProductResponse object
        productResponse.setTotalElements(pageProducts.getTotalElements());
        //set total number of elements to ProductResponse object
        productResponse.setTotalPages(pageProducts.getTotalPages());
        //set total number of pages to ProductResponse object
        productResponse.setLastPage(pageProducts.isLast());
        //set whether it is the last page or not to ProductResponse object

        return productResponse;
        //return ProductResponse object to controller
        //ProductDTO is a Data Transfer Object that contains product details
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        //search products by keyword from database

        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        //create Sort object based on sortBy and sortOrder parameters

        Pageable pageDetails= PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        //create Pageable object based on pageNumber, pageSize and sortByAndOrder parameters

        Page<Product> pageProducts=productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%', pageDetails);
        //fetch paginated products from database


//        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%', pageDetails);
        //convert list of product entities to list of product DTOs
        //findByProductNameLikeIgnoreCase is a custom method in ProductRepository that searches products by name ignoring case

        List<Product> products =pageProducts.getContent();
        //get list of products from Page object

        List<ProductDTO> productDTOS = products.stream().
                map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        //create ProductResponse object
        //ProductResponse is a custom class that contains list of product DTOs and other pagination details
        //ProductDTO is a Data Transfer Object that contains product details

        //Adding validation for no products found
        if(products.isEmpty()){
            throw new APIException("No products found matching the keyword: " + keyword);
        }//if no products found, throw APIException No products found matching the keyword


        ProductResponse productResponse = new ProductResponse();
        //ProductResponse object to set list of product DTOs
        productResponse.setContent(productDTOS);
        //set list of product DTOs to ProductResponse object
        //setContent method sets the list of product DTOs to the ProductResponse object

        productResponse.setPageNumber(pageProducts.getNumber());
        //set current page number to ProductResponse object
        productResponse.setPageSize(pageProducts.getSize());
        //set page size to ProductResponse object
        productResponse.setTotalElements(pageProducts.getTotalElements());
        //set total number of elements to ProductResponse object
        productResponse.setTotalPages(pageProducts.getTotalPages());
        //set total number of pages to ProductResponse object
        productResponse.setLastPage(pageProducts.isLast());
        //set whether it is the last page or not to ProductResponse object

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
//        String path="images/";
        //We will add this path in application.properties file here its hardcoded
        String fileName=fileService.uploadImage(path, image);
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


}
