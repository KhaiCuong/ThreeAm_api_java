package com.project.threeam.repositories;

import com.project.threeam.entities.AutionEntity;
import com.project.threeam.entities.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BidRepository extends JpaRepository<BidEntity, Long > {
    Optional<BidEntity> findByBidId(Long bidId);

}
