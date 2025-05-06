package com.ecommerce.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

//this particular class is an entity and it will create a table in db with class name so in db table name is - category
@Entity
//or
//if we need give sab specific table name so if db table name is-categories
//@Entity(name="catgories")
public class Category {
    //categoryId to be our unique identifier anf in db it is marked as primary key
    @Id
    //setting the attributes of category
    private Long categoryId;
    private String categoryName;

    //constructor
    public Category(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    //getter
    public Long getCategoryId() {
        return categoryId;
    }

    //setter
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    //getter
    public String getCategoryName() {
        return categoryName;
    }

    //setter
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
