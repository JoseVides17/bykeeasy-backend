package com.bykeeasy.infrastructure.adapter.out.persistence.repository;

import com.bykeeasy.infrastructure.adapter.out.persistence.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataDriverRepository extends JpaRepository<DriverEntity, String> {
    Optional<DriverEntity> findByUser_Email(String email);
}
