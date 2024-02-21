package com.project.threeam.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrderDetailDTO {

    private Long detailId;

    @Positive(message = "Quantity must be a positive value")
    @NotNull(message = "quantity is not empty")
    @Min(value = 1, message = "Value must be at least 0")
    @Max(value = 30, message = "Value must be at most 30")
    private Integer quantity;

    @Positive(message = "Quantity must be a positive value")
    @NotNull(message = "quantity is not empty")
    private Double price;

    @NotEmpty(message = "Product Name is not empty")
    @Size(min = 3,  max = 30, message = "Product Name must be 3-20 characters")
    private String produc_name;

    private String image;

    @NotNull(message = "Order id is not empty")
    @Positive(message = "Order id must be a positive value")
    private Long orderId;

    @NotEmpty(message = "Product id is not empty")
    private String productId;

    private Boolean CanFeedBack ;

}
