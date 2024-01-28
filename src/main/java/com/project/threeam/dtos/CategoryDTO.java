package com.project.threeam.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO extends BaseDTO {
    @NotEmpty(message = "Category ID is not empty")
    @Size(min = 3, max = 20, message = "Category ID must be 3-20 characters")
    private String categoryId;

    @NotEmpty(message = "Category Name is not empty")
    @Size(min = 3,  max = 30, message = "Category Name must be 3-20 characters")
    private String category_name;

    private Boolean Status;

//    @Size(max = 255, message = "Description must be at most 255 characters")
    private String description;

}
