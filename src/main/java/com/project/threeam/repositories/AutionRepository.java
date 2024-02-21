package com.project.threeam.repositories;

import com.project.threeam.entities.AutionEntity;
import com.project.threeam.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutionRepository extends JpaRepository<AutionEntity, Long > {
    Optional<AutionEntity> findByAutionId(Long orderId);
}
