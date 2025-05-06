package com.ecommerce.project.model;

public class Category {
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
