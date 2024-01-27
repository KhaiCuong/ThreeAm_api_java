package com.project.threeam.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.threeam.entities.enums.OrderStatusEnum;
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
@Table(name = "orderTb")
public class OrderEntity extends BaseEntity {
    @Id
    @Column(name = "order_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "phone_number", nullable = false)
    private String phone_number;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name="status")
    private OrderStatusEnum Status;

    @Column(name = "username", nullable = false)
    private String username;

    @OneToMany(mappedBy = "orderEntity",fetch = FetchType.LAZY ,orphanRemoval = true)
    @JsonManagedReference
    private Set<OrderDetailEntity> orderDetailEntities;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity userOrderEntity;

    @OneToOne(mappedBy = "orderPaymentEntity")
    @JsonBackReference
    private PaymentEntity paymentEntity;

}
