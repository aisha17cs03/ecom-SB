package com.ecommerce.project.Service;

import com.ecommerce.project.Repositories.CategoryRepository;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService{
    //doing a mapping that will return all the categories
  //  private List<Category> categories = new ArrayList<>();
    //private Long nextId=1L;
    
    //getting an instance of category repository so use autowired annotaion
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        
        //find all method is going to return all the categories that exist in the db
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
       // category.setCategoryId(nextId++);
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category= categoryRepository.findById(categoryId)
                //ResourceNotFoundException is a custom exception that we have created to handle the resource not found exception in ResourceNotFoundException.java file
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));


        categoryRepository.delete(category);
        return "Category with categoryId: " + categoryId + " deleted successfully!!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Category savedCategory= categoryRepository.findById(categoryId)
                //ResourceNotFoundException is a custom exception that we have created to handle the resource not found exception in ResourceNotFoundException.java file
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        category.setCategoryId(categoryId);
        savedCategory=categoryRepository.save(category);
        return savedCategory;
    }
}
