package com.project.threeam.dtos;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.threeam.entities.OrderDetailEntity;
import com.project.threeam.entities.UserEntity;
import com.project.threeam.entities.enums.OrderStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class OrderDTO {

    private Long orderId;

    @NotEmpty(message = "Phone number is not empty")
    @Size(min = 7,  max = 12, message = "Phone number must be 7-12 characters")
    private String phone_number;

    @NotEmpty(message = "Address is not empty")
    private String address;

    private OrderStatusEnum Status;

    @NotEmpty(message = "Username is not empty")
    private String username;

    private Integer quantity;

    private Double totalPrice;

    private String image;

    @NotNull(message = "User id is not empty")
    @Positive(message = "User id must be a positive value")
    private Long userId;
}
