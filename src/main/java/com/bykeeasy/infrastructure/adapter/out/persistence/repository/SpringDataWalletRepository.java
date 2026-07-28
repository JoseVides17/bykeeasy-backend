package com.bykeeasy.infrastructure.adapter.out.persistence.repository;

import com.bykeeasy.infrastructure.adapter.out.persistence.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataWalletRepository extends JpaRepository<WalletEntity, String> {
    Optional<WalletEntity> findByUser_Id(String userId);
}
