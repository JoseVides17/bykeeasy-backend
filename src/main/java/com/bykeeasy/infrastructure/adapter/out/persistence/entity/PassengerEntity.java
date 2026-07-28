package com.bykeeasy.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "passengers")
@Data
public class PassengerEntity {
    
    @Id
    private String userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
