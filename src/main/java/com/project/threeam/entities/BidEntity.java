package com.project.threeam.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bid")
public class BidEntity extends BaseEntity {

    @Id
    @Column(name = "bid_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidId;

    @Column(name="pid_price")
    private Double pidPice;

    @Column(name="userName")
    private String userName;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "aution_id", referencedColumnName = "aution_id")
    private AutionEntity autionBidEntity;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity userBidEntity;
}
