package com.ecommerce.project.Service;

import com.ecommerce.project.Payload.CategoryDTO;
import com.ecommerce.project.Payload.CategoryResponse;
import com.ecommerce.project.Repositories.CategoryRepository;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImplementation implements CategoryService{
    //doing a mapping that will return all the categories id is managed by db
  //  private List<Category> categories = new ArrayList<>();
    //private Long nextId=1L;
    
    //getting an instance of category repository so use autowired annotaion
    //@autowired is used to inject the CategoryRepository bean
    @Autowired


    //CategoryRepository is an interface that extends JpaRepository
    private CategoryRepository categoryRepository;

    //getting an instance of ModelMapper so use autowired annotaion
    //Autowired is used to inject the ModelMapper bean
    @Autowired

    //ModelMapper is used to map the entity to DTO and vice versa
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        //this method is used to find the category
        List<Category> categories = categoryRepository.findAll();
        //adding a condition/validation to check if the categories is empty or not
        //if categories is empty then we are throwing an exception

        if(categories.isEmpty())
            //if categories is empty then we are throwing an exception
            throw new APIException("No category created till now");

        List<CategoryDTO> categoryDTOS = categories.stream().
                map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        //setting the categoryDTOs to the categoryResponse
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category=modelMapper.map(categoryDTO,Category.class);
       // category.setCategoryId(nextId++);
        Category categoryFromDb= categoryRepository.findByCategoryName(category.getCategoryName());
        if(categoryFromDb != null){
            //if category is already present then we are throwing an exception
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists !!!");
        }
        Category savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
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
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category savedCategory= categoryRepository.findById(categoryId)
                //ResourceNotFoundException is a custom exception that we have created to handle the resource not found exception in ResourceNotFoundException.java file
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        Category category=modelMapper.map(categoryDTO,Category.class);
        category.setCategoryId(categoryId);
        savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}
