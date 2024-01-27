package com.project.threeam.repositories;

import com.project.threeam.dtos.ProductDTO;
import com.project.threeam.entities.CategoryEntity;
import com.project.threeam.entities.ProductEntity;
import com.project.threeam.entities.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long > {
    Optional<List<ProductImageEntity>> findByProductEntity(ProductEntity product_id);

}
