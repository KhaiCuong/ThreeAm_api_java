package com.project.threeam.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "productImage")
public class ProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", unique = true)
    private Long imageId;

    @Column(name = "image_url", nullable = false)
    private String image_url;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductEntity productEntity;

}
