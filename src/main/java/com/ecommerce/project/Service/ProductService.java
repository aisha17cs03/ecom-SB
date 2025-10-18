package com.ecommerce.project.Service;

import com.ecommerce.project.Payload.ProductDTO;
import com.ecommerce.project.Payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO product);

    ProductResponse getAllProducts();

    ProductResponse getProductsByCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDTO upadateProduct(Long productId, ProductDTO product);

    ProductDTO deleteProduct(Long productId);

    ProductDTO upadateProductImage(Long productId, MultipartFile image) throws IOException;
}
