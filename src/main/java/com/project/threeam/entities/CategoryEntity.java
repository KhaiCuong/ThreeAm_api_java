package com.project.threeam.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "category")
public class CategoryEntity extends BaseEntity {
    @Id
    @Column(name = "category_id", unique = true)
    private String categoryId;

    @Column(name = "category_name", nullable = false)
    private String category_name;

    @Column(name="status")
    private Boolean Status;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(mappedBy = "categoryEntity",fetch = FetchType.LAZY ,orphanRemoval = true)
    @JsonManagedReference
    private Set<ProductEntity> productEntity;
}
