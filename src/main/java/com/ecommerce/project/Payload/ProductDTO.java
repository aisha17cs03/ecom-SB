package com.ecommerce.project.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Data annotation generates getters, setters, toString, equals, and hashCode methods
//@NoArgsConstructor generates a no-argument constructor
//@AllArgsConstructor generates a constructor with 1 parameter for each field in your class
public class ProductDTO {
    private Long productId;
    private String productName;
    private String description;
    private String image;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;
}
