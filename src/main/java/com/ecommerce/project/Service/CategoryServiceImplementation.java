package com.ecommerce.project.Service;

import com.ecommerce.project.Payload.CategoryDTO;
import com.ecommerce.project.Payload.CategoryResponse;
import com.ecommerce.project.Repositories.CategoryRepository;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    //PageNumber is the number of the page that we want to retrieve
    //PageSize is the number of the records that we want to retrieve in a page
    //PageNumber and PageSize are passed as request parameters in the URL.
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize) {
        //Pageable is an interface that provides pagination information
        //pageDetails is an object of pageable interface
        //PageRequest is a class that implements the pageable interface
        //Page <Category> is a class that provides pagination information for category entity
        Pageable pageDetails= PageRequest.of(pageNumber, pageSize);
        Page<Category> categoryPage= categoryRepository.findAll(pageDetails);

        //getting the content of the page
        // getContent() method is used to get the content of the page
        //it returns a list of categories
        List<Category> categories=categoryPage.getContent();
        //this method is used to find the category
//        List<Category> categories = categoryRepository.findAll();
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
        //information(metadata) abount the pagination
        //setting the pagination information to the categoryResponse
        //setting the page number, page size, total elements, total pages and last page to the categoryResponse.

        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
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
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category= categoryRepository.findById(categoryId)
                //ResourceNotFoundException is a custom exception that we have created to handle the resource not found exception in ResourceNotFoundException.java file
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));



        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
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
