package com.ecommerce.project.Controller;


import com.ecommerce.project.Payload.CategoryDTO;
import com.ecommerce.project.Payload.CategoryResponse;
import com.ecommerce.project.Service.CategoryService;
import com.ecommerce.project.model.Category;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//Requestmapping in class level
@RequestMapping("/api")//api is common all the method of mappping so this common thing we write in request mapping and every
//method we will remove /api
public class CategoryController {

    private CategoryService categoryService;
    //constructor


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    //or below is the request mapping at method level alternative method of api mapping
    //@RequestMapping(value = "/api/public/categories", method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getAllCategories() {
       CategoryResponse categoryResponse = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    //adding a new categories with the help of post mapping request
    @PostMapping("/public/categories")
    //or below is the request mapping at method level alternative method of api mapping
    //@RequestMapping(value = "/api/public/categories", method = RequestMethod.POST)
    //@Valid is used to validate the request body and make sure that the category name is not blank its checking the whether request is fulfilling or not if not it throws an 400 bad request response
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        //adding a category here
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }


    @DeleteMapping("/admin/categories/{categoryId}")
    //or below is the request mapping at method level alternative method of api mapping
    //@RequestMapping(value = "/api/public/categories", method = RequestMethod.DELETE)
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){
       // try {
            CategoryDTO deleteCategory= categoryService.deleteCategory(categoryId);
            //way-1
            return new ResponseEntity<>(deleteCategory, HttpStatus.OK);
            // we have other method also for creating an ResponseEntity
            //way-2
            //return ResponseEntity.ok(status);
            //way-3
            //return ResponseEntity.status(HttpStatus.OK).body(status);
       // } catch (ResponseStatusException e){
           // return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    @PutMapping("/public/categories/{categoryId}")
    //or below is the request mapping at method level alternative method of api mapping
    //@RequestMapping(value = "/api/public/categories", method = RequestMethod.PUT)
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                 @PathVariable Long categoryId){
        // try{
        CategoryDTO savedCategoryDTO= categoryService.updateCategory(categoryDTO,categoryId);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
        //}catch (ResponseStatusException e){
        // return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        //}
    }

}


