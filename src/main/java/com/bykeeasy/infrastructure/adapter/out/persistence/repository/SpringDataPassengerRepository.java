package com.bykeeasy.infrastructure.adapter.out.persistence.repository;

import com.bykeeasy.infrastructure.adapter.out.persistence.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataPassengerRepository extends JpaRepository<PassengerEntity, String> {
    Optional<PassengerEntity> findByUser_Email(String email);
}
