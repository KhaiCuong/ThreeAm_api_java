package com.project.threeam.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.threeam.entities.enums.RoleEnum;
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
@Table(name = "usertb")
public class UserEntity extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "phone_number", nullable = false)
    private String phone_number;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_verified", nullable = true)
    private Boolean verify;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @OneToMany(mappedBy = "userOrderEntity",fetch = FetchType.LAZY ,orphanRemoval = true)
    @JsonManagedReference
    private Set<OrderEntity> orderEntity;

    @OneToMany(mappedBy = "userFeedbackEntity",fetch = FetchType.LAZY ,orphanRemoval = true)
    @JsonManagedReference
    private Set<FeedbackEntity> FeedbackEntities;

    @OneToMany(mappedBy = "userBidEntity",fetch = FetchType.LAZY ,orphanRemoval = true)
    @JsonManagedReference
    private Set<BidEntity> bidEntities;
}
