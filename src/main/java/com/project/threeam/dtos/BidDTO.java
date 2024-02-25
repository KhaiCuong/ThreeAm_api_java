package com.project.threeam.dtos;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.threeam.entities.AutionEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class BidDTO extends BaseDTO{

    private Long bidId;

    private Double pidPice;

    private String userName;

    private Long autionId;

    private Long userId;

}
