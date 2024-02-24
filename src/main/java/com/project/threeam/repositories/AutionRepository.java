package com.project.threeam.repositories;

import com.project.threeam.entities.AutionEntity;
import com.project.threeam.entities.OrderDetailEntity;
import com.project.threeam.entities.OrderEntity;
import com.project.threeam.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutionRepository extends JpaRepository<AutionEntity, Long > {
    Optional<AutionEntity> findByAutionId(Long orderId);

    Optional<AutionEntity> findByAutionProductEntity(ProductEntity productEntity);

}
