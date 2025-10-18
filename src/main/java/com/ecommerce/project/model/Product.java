package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    //GeneratedValue is used to generate the primary key automatically
    //and GenerationType.AUTO is used to generate the primary key based on the database dialect
    @GeneratedValue(strategy = GenerationType.AUTO)
    //Primary key for the product entity
    //productId is the primary key and productName is the name of the product and description is the description of the product and quantity is the quantity of the product and price is the price of the product and specialPrice is the special price of the product and category is the category of the product
    private Long productId;
    @NotBlank
    //NotBlank is used to validate that the product name is not blank
    @Size(min=3, message="Product name must be at least 3 characters")
    //Size is used to validate that the product name is at least 3 characters long
    private String productName;
    private String image;
    @NotBlank
    //NotBlank is used to validate that the description is not blank
    @Size(min=6, message="Description must be at least 6 characters")
    //Size is used to validate that the description is at least 6 characters long
    private String description;
    private Integer quantity;
    private double price;//Rs.100
    private double discount;//25%
    private double specialPrice;//Rs.75

    @ManyToOne
    //Many products can belong to one category
    //many-to-one relationship between product and category
    @JoinColumn(name="category_id")
    //Foreign key column to reference the category entity
    //category field to reference the Category entity
    //JoinColumn is used to specify the foreign key column
    //Category is the entity class and category_id is the foreign key column
    private Category category;
}
