package com.ecommerce.project.Service;

import com.ecommerce.project.Payload.ProductDTO;
import com.ecommerce.project.model.Product;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);
}
