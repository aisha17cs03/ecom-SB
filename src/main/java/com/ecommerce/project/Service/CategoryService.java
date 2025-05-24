package com.ecommerce.project.Service;

import com.ecommerce.project.Payload.CategoryResponse;
import com.ecommerce.project.model.Category;

import java.util.List;

//making use of interface to promote the loose coupling and modularity in my code base
public interface CategoryService {
    CategoryResponse getAllCategories();

    void createCategory(Category category);

    String deleteCategory(Long categoryId);

    Category updateCategory(Category category, Long categoryId);
}
