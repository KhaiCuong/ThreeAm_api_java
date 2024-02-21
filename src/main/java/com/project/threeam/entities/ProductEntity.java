package com.project.threeam.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.threeam.entities.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class ProductEntity extends BaseEntity  {

    @Id
    @Column(name = "product_id", unique = true)
    private String productId;

    @Column(name = "produc_name", nullable = false)
    private String produc_name;

    @Column(name = "instock", nullable = false)
    private Integer instock;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name="status")
    private Boolean Status;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "diameter")
    private Double diameter;

    @Column(name="is_waterproof")
    private Boolean isWaterproof;

    @Column(name = "gender")
    private GenderEnum gender;

    @Column(name = "image", nullable = true)
    private String image;

    @Column(name = "total_buy", nullable = true)
    private Integer totalBuy;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private CategoryEntity categoryEntity;

    @OneToMany(mappedBy = "productEntity",fetch = FetchType.LAZY ,orphanRemoval = true)
    @JsonManagedReference
    private Set<ProductImageEntity> productImageEntities;

    @OneToMany(mappedBy = "productDetailEntity",fetch = FetchType.LAZY ,orphanRemoval = true)
    @JsonManagedReference
    private Set<OrderDetailEntity> orderDetailEntities;

    @OneToMany(mappedBy = "productFeedbackEntity",fetch = FetchType.LAZY ,orphanRemoval = true)
    @JsonManagedReference
    private Set<FeedbackEntity> FeedbackEntities;

    @OneToOne(mappedBy = "autionProductEntity")
    @JsonBackReference
    private AutionEntity autionEntity;
}

