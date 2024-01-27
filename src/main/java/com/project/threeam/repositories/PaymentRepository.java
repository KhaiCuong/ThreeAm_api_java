package com.project.threeam.repositories;

import com.project.threeam.entities.OrderEntity;
import com.project.threeam.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long > {

    Optional<PaymentEntity> findByOrderPaymentEntity(OrderEntity orderId);


}
