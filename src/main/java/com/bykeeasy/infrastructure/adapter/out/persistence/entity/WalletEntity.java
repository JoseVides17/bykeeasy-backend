package com.bykeeasy.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
@Getter
@Setter
public class WalletEntity {
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private BigDecimal balance;
}
