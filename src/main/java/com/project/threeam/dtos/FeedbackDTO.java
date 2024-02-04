package com.project.threeam.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.threeam.entities.ProductEntity;
import com.project.threeam.entities.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FeedbackDTO {

    private String title;

    @NotEmpty(message = "Content is not empty")
    private String content;

    @NotNull(message = "Instock is not empty")
    @Min(value = 0, message = "Value must be at least 0")
    @Max(value = 5, message = "Value must be at most 5")
    private Integer start;

    @NotNull(message = "User id is not empty")
    @Positive(message = "User id must be a positive value")
    private Long userId;

    @NotEmpty(message = "Product id is not empty")
    private String productId;
}
