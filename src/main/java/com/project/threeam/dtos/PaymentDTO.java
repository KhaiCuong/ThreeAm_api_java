package com.project.threeam.dtos;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.threeam.entities.OrderEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentDTO {
    private Boolean Status;

    @NotEmpty(message = "Fullname is not empty")
    private String fullname;

    @NotNull(message = "Order id is not empty")
    @Positive(message = "Order id must be a positive value")
    private Long order_id;
}
