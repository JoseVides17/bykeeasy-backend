package com.bykeeasy.application.service;

import com.bykeeasy.domain.model.Transaction;
import com.bykeeasy.domain.model.Wallet;
import com.bykeeasy.application.port.in.WalletUseCase;
import com.bykeeasy.application.port.out.PaymentGatewayPort;
import com.bykeeasy.application.port.out.WalletRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class WalletService implements WalletUseCase {

    private final WalletRepositoryPort walletRepository;
    private final PaymentGatewayPort paymentGateway;

    public WalletService(WalletRepositoryPort walletRepository, PaymentGatewayPort paymentGateway) {
        this.walletRepository = walletRepository;
        this.paymentGateway = paymentGateway;
    }

    @Override
    public Wallet getWalletByUserId(String userId) {
        return walletRepository.findByUserId(userId)
                .orElseGet(() -> walletRepository.save(new Wallet(null, userId, BigDecimal.ZERO)));
    }

    @Override
    @Transactional
    public void topUp(String userId, BigDecimal amount, String paymentMethodId) {
        Wallet wallet = getWalletByUserId(userId);
        
        boolean success = paymentGateway.processPayment(wallet.getId(), amount, paymentMethodId);
        
        if (success) {
            wallet.deposit(amount);
            walletRepository.save(wallet);
            
            Transaction transaction = new Transaction(
                    UUID.randomUUID().toString(),
                    wallet.getId(),
                    amount,
                    "CREDIT",
                    "Top-up via payment gateway",
                    LocalDateTime.now()
            );
            walletRepository.saveTransaction(transaction);
        } else {
            throw new RuntimeException("Payment failed");
        }
    }

    @Override
    @Transactional
    public void debit(String userId, BigDecimal amount, String description) {
        Wallet wallet = getWalletByUserId(userId);
        
        wallet.debit(amount);
        walletRepository.save(wallet);
        
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                wallet.getId(),
                amount,
                "DEBIT",
                description,
                LocalDateTime.now()
        );
        walletRepository.saveTransaction(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByUserId(String userId) {
        Wallet wallet = getWalletByUserId(userId);
        return walletRepository.findTransactionsByWalletId(wallet.getId());
    }
}
