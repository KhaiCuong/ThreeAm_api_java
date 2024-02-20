package com.project.threeam.repositories;

import com.project.threeam.entities.CategoryEntity;
import com.project.threeam.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String > {
    Optional<CategoryEntity> findByCategoryId(String id);
    List<CategoryEntity> findAllByOrderByCategoryIdAsc();

}
