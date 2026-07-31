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
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WalletPersistenceAdapter implements WalletRepositoryPort {

    private final SpringDataWalletRepository walletRepository;
    private final SpringDataTransactionRepository transactionRepository;
    private final SpringDataUserRepository userRepository;

    @Override
    public Wallet save(Wallet wallet) {
        UserEntity user = userRepository.findById(wallet.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found for wallet creation: " + wallet.getUserId()));
                
        WalletEntity entity = PersistenceMapper.toEntity(wallet);
        entity.setUser(user);
        
        WalletEntity saved = walletRepository.save(entity);
        return PersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Wallet> findByUserId(String userId) {
        return walletRepository.findByUser_Id(userId)
                .map(PersistenceMapper::toDomain);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        TransactionEntity entity = PersistenceMapper.toEntity(transaction);
        TransactionEntity saved = transactionRepository.save(entity);
        return PersistenceMapper.toDomain(saved);
    }

    @Override
    public List<Transaction> findTransactionsByWalletId(String walletId) {
        return transactionRepository.findByWalletIdOrderByTimestampDesc(walletId).stream()
                .map(PersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }
}
