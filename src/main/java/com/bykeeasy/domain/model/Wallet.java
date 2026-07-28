package com.bykeeasy.domain.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Wallet {
    private final String id;
    private final String userId;
    private BigDecimal balance;
    private final String currency;

    public Wallet(String id, String userId, BigDecimal balance) {
        this(id, userId, balance, "COP");
    }

    public Wallet(String id, String userId, BigDecimal balance, String currency) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.currency = currency;
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.balance = this.balance.add(amount);
    }

    public void debit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }
        this.balance = this.balance.subtract(amount);
    }
}
