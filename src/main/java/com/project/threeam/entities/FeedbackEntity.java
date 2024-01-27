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
@Table(name = "feedback")
public class FeedbackEntity extends BaseEntity {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = true)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "start", nullable = false)
    private Integer start;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity userFeedbackEntity;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductEntity productFeedbackEntity;
}
