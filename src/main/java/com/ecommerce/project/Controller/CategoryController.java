package com.ecommerce.project.Controller;


import com.ecommerce.project.Service.CategoryService;
import com.ecommerce.project.model.Category;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
    public ResponseEntity<List<Category>> getAllCategories() {
        final List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //adding a new categories with the help of post mapping request
    @PostMapping("/public/categories")
    //or below is the request mapping at method level alternative method of api mapping
    //@RequestMapping(value = "/api/public/categories", method = RequestMethod.POST)
    //@Valid is used to validate the request body and make sure that the category name is not blank its checking the whether request is fulfilling or not if not it throws an 400 bad request response
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
        //adding a category here
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
    }


    @DeleteMapping("/admin/categories/{categoryId}")
    //or below is the request mapping at method level alternative method of api mapping
    //@RequestMapping(value = "/api/public/categories", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        try {
            String status = categoryService.deleteCategory(categoryId);
            //way-1
            return new ResponseEntity<>(status, HttpStatus.OK);
            // we have other method also for creating an ResponseEntity
            //way-2
            //return ResponseEntity.ok(status);
            //way-3
            //return ResponseEntity.status(HttpStatus.OK).body(status);
        } catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("/public/categories/{categoryId}")
    //or below is the request mapping at method level alternative method of api mapping
    //@RequestMapping(value = "/api/public/categories", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCategory(@RequestBody Category category,
                                                 @PathVariable Long categoryId){
        try{
            Category savedCategory= categoryService.updateCategory(category,categoryId);
            return new ResponseEntity<>("Updated category with categoryId: " + category, HttpStatus.OK);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }
}

