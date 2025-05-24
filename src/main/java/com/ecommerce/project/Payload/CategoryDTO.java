package com.ecommerce.project.Payload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Lombok will generate the getters and setters for the fields
//@Data annotation will generate the getters and setters for the fields
// @NoArgsConstructor annotation will generate the default constructor
// @AllArgsConstructor annotation will generate the parameterized constructor

@Data
@NoArgsConstructor
@AllArgsConstructor

// This class is used to send the response for a category
// This class is used to represent the response for a category
// It contains the category ID and category name
// This class is used to send the response for a category


public class CategoryDTO {
    private Long categoryId;
    private String categoryName;
}
