package com.bykeeasy.application.port.out;

import com.bykeeasy.domain.model.Wallet;
import com.bykeeasy.domain.model.Transaction;

import java.util.Optional;
import java.util.List;

public interface WalletRepositoryPort {
    Wallet save(Wallet wallet);
    Optional<Wallet> findByUserId(String userId);
    Transaction saveTransaction(Transaction transaction);
    List<Transaction> findTransactionsByWalletId(String walletId);
}
