package com.ecommerce.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//this particular class is an entity and it will create a table in db with class name so in db table name is - category
//@Entity
//or
//if we need give sab specific table name so if db table name is-categories
@Entity(name="categories")
//adding lombok annotation to remove boilerplate code and remove all constructors getters and setters
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    //categoryId to be our unique identifier and in db it is marked as primary key
    @Id
    //Identity strategy used in the db to genrate primary key values and this supported by relational db's like-postgresql, mysql
    //Note-Identity strategy is not supported by all the db's
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //setting the attributes of category
    private Long categoryId;


    //Adding @NotBlank annotation to make sure that the category name is not blank and it is mandatory to provide a category name
    //so now in postman if we do not provide category name then it will throw an error 500 internal server error
    @NotBlank
    private String categoryName;


//    //constructor
//    public Category(Long categoryId, String categoryName) {
//        this.categoryId = categoryId;
//        this.categoryName = categoryName;
//    }
//
//
//    //default constructor- write for good practice not mandatory to write
//    public Category(){
//
//    }
//
//    //getter
//    public Long getCategoryId() {
//        return categoryId;
//    }
//
//    //setter
//    public void setCategoryId(Long categoryId) {
//        this.categoryId = categoryId;
//    }
//
//    //getter
//    public String getCategoryName() {
//        return categoryName;
//    }
//
//    //setter
//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }
}
