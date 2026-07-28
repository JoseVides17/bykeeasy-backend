package com.bykeeasy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String id;
    private String walletId;
    private BigDecimal amount;
    private String type; // CREDIT, DEBIT
    private String description;
    private LocalDateTime timestamp;
}
