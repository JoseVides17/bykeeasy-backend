package com.bykeeasy.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "passengers")
@Getter
@Setter
public class PassengerEntity implements Persistable<String> {
    
    @Id
    private String userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Transient
    private boolean isNew = true;

    @Nullable
    @Override
    public String getId() {
        return this.userId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @jakarta.persistence.PostLoad
    @jakarta.persistence.PostPersist
    void markNotNew() {
        this.isNew = false;
    }
}
