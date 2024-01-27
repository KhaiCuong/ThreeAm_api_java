package com.project.threeam.repositories;

import com.project.threeam.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long > {
    Optional<List<FeedbackEntity>> findByProductFeedbackEntity(ProductEntity productId);
}
