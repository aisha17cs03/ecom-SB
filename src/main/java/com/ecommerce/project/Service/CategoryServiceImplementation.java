package com.ecommerce.project.Service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService{
    //doing a mapping that will return all the categories
    private List<Category> categories = new ArrayList<>();
    private Long nextId=1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        //below list is converted into the stream
        Category category=categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Response not found"));

        categories.remove(category);
        return "Category with categoryId: " + categoryId + " deleted successfully!!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional <Category> optinalCategory= categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId)).findFirst();

        if(optinalCategory.isPresent()){
           Category existingCategory=optinalCategory.get();
           existingCategory.setCategoryName(category.getCategoryName());
           return existingCategory;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
    }
}
