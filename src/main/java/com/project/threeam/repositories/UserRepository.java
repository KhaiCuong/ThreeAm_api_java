package com.project.threeam.repositories;

import com.project.threeam.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>  {
    Optional<UserEntity> findByUserId(Long id);
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.verificationCode = ?1")
    Optional<UserEntity> findByVerificationCode(String code);
}
