package com.project.threeam.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "aution")
public class AutionEntity extends BaseEntity {
    @Id
    @Column(name = "aution_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long autionId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="status")
    private Boolean status;

    @Column(name="lastBidPrice")
    private Double lastBidPrice;

    private String image;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @OneToOne
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private ProductEntity autionProductEntity;

    @OneToMany(mappedBy = "autionBidEntity",fetch = FetchType.LAZY ,orphanRemoval = true)
    @JsonManagedReference
    private Set<BidEntity> bidEntities;


}
