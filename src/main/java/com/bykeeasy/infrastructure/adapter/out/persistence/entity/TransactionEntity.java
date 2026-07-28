package com.bykeeasy.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class TransactionEntity implements Persistable<String> {
    @Id
    private String id;
    private String walletId;
    private BigDecimal amount;
    private String type;
    private String description;
    private LocalDateTime timestamp;

    @Transient
    private boolean isNew = true; // Controla si es un INSERT o UPDATE

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    // Puedes marcar isNew como false en métodos @PostLoad o @PostPersist
    @jakarta.persistence.PostLoad
    @jakarta.persistence.PostPersist
    void markNotNew() {
        this.isNew = false;
    }
}
