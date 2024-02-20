package com.project.threeam.dtos;

import com.project.threeam.entities.CategoryEntity;
import com.project.threeam.entities.enums.GenderEnum;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductDTO extends BaseDTO  {
    @NotEmpty(message = "Product ID is not empty")
    @Size(min = 3, max = 20, message = "Product ID must be 3-20 characters")
    private String productId;

    @NotEmpty(message = "Product Name is not empty")
    @Size(min = 3,  max = 30, message = "Product Name must be 3-20 characters")
    private String produc_name;

    @NotNull(message = "Instock is not empty")
    @Positive(message = "Instock must be a positive value")
    private Integer instock;

    @Positive(message = "Price must be a positive value")
    private Double price;

    private Boolean Status;

    private Boolean isWaterproof;

    private Double diameter;

    private GenderEnum gender;


    private Integer totalBuy;

    //    @Size(max = 255, message = "Description must be at most 255 characters")
    private String description;

    private String image;

    @Size(max = 255, message = "category_id must be at most 255 characters")
    private String categoryId;



}
