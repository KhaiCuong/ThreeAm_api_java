package com.project.threeam.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.Order;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payment")
public class PaymentEntity extends BaseEntity {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private Boolean Status;

    @Column(name = "fullname", nullable = false)
    private String fullname;


    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonManagedReference
    private OrderEntity orderPaymentEntity;

}
