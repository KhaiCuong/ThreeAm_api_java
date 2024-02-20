package com.project.threeam.repositories;

import com.project.threeam.entities.CategoryEntity;
import com.project.threeam.entities.OrderEntity;
import com.project.threeam.entities.ProductEntity;
import com.project.threeam.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long > {
    Optional<OrderEntity> findByOrderId(Long orderId);

    Optional<List<OrderEntity>> findByUserOrderEntity(UserEntity userId);

    List<OrderEntity> findAllByOrderByOrderIdAsc();

}
