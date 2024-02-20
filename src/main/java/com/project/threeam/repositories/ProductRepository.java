package com.project.threeam.repositories;

import com.project.threeam.entities.CategoryEntity;
import com.project.threeam.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    Optional<ProductEntity> findByProductId(String product_id);

    List<ProductEntity> findAllByOrderByProductIdAsc();


    Optional<List<ProductEntity>> findByCategoryEntity(CategoryEntity category_Id);

}