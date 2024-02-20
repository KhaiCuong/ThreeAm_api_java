package com.project.threeam.repositories;

import com.project.threeam.entities.OrderDetailEntity;
import com.project.threeam.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long > {
    Optional<OrderDetailEntity> findByDetailId(Long detailId);

    List<OrderDetailEntity> findAllByOrderByDetailIdDesc();

    Optional<List<OrderDetailEntity>> findByOrderEntity(OrderEntity orderId);
}
