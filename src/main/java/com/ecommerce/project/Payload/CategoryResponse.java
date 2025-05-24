package com.ecommerce.project.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


// Lombok will generate the getters and setters for the fields
// @Data annotation will generate the getters and setters for the fields
// @NoArgsConstructor annotation will generate the default constructor
// @AllArgsConstructor annotation will generate the parameterized constructor

@Data
@NoArgsConstructor
@AllArgsConstructor

// This class is used to send the response for a list of categories
// It contains a list of CategoryDTO objects
// This class is used to represent the response for a list of categories
// It contains the content field which is a list of CategoryDTO objects

public class CategoryResponse {
    private List<CategoryDTO> content;

}
