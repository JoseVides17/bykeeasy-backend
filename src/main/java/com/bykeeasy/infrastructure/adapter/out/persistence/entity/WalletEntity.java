package com.bykeeasy.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
@Data
public class WalletEntity implements Persistable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private BigDecimal balance;

    @Transient // No se persiste en la BD
    private boolean isNew = true;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    // Callbacks de JPA para marcar la entidad como persistida
    @PostLoad
    @PostPersist
    void markNotNew() {
        this.isNew = false;
    }

}
