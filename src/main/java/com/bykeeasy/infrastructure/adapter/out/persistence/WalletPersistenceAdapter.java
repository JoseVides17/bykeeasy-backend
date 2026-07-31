package com.bykeeasy.infrastructure.adapter.out.persistence;

import com.bykeeasy.application.port.out.WalletRepositoryPort;
import com.bykeeasy.domain.model.Transaction;
import com.bykeeasy.domain.model.Wallet;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.TransactionEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.WalletEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataTransactionRepository;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataUserRepository;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataWalletRepository;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

public class WalletPersistenceAdapter implements WalletRepositoryPort {

    private final SpringDataWalletRepository walletRepository;
    private final SpringDataTransactionRepository transactionRepository;
    private final SpringDataUserRepository userRepository;
    private final EntityManager entityManager;

    public WalletPersistenceAdapter(SpringDataWalletRepository walletRepository, 
                                    SpringDataTransactionRepository transactionRepository, 
                                    SpringDataUserRepository userRepository, 
                                    EntityManager entityManager) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Wallet save(Wallet wallet) {
        WalletEntity entity = PersistenceMapper.toEntity(wallet);
        
        if (wallet.getUserId() != null) {
            UserEntity user = userRepository.findById(wallet.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found for wallet creation: " + wallet.getUserId()));
            entity.setUser(user);
        }
        
        WalletEntity saved = entityManager.merge(entity);
        return PersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Wallet> findByUserId(String userId) {
        return walletRepository.findByUser_Id(userId)
                .map(PersistenceMapper::toDomain);
    }

    @Override
    @Transactional
    public Transaction saveTransaction(Transaction transaction) {
        TransactionEntity entity = PersistenceMapper.toEntity(transaction);
        TransactionEntity saved = entityManager.merge(entity);
        return PersistenceMapper.toDomain(saved);
    }

    @Override
    public List<Transaction> findTransactionsByWalletId(String walletId) {
        return transactionRepository.findByWalletIdOrderByTimestampDesc(walletId).stream()
                .map(PersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }
}
