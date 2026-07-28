package com.bykeeasy.infrastructure.adapter.out.persistence.repository;

import com.bykeeasy.infrastructure.adapter.out.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataTransactionRepository extends JpaRepository<TransactionEntity, String> {
    List<TransactionEntity> findByWalletIdOrderByTimestampDesc(String walletId);
}
