package com.project.threeam.dtos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.threeam.entities.BidEntity;
import com.project.threeam.entities.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Data
public class AutionDTO extends BaseDTO{

    private Long autionId;

    private String name;

    private Boolean Status;

    private Double lastBidPrice;

    private String image;

    private Timestamp startTime;

    private Timestamp endTime;

    private String productId;


}
