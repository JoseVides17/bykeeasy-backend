package com.bykeeasy.application.port.in;

import com.bykeeasy.domain.model.Wallet;
import com.bykeeasy.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface WalletUseCase {
    Wallet getWalletByUserId(String userId);
    void topUp(String userId, BigDecimal amount, String paymentMethodId);
    void debit(String userId, BigDecimal amount, String description);
    List<Transaction> getTransactionsByUserId(String userId);
}
