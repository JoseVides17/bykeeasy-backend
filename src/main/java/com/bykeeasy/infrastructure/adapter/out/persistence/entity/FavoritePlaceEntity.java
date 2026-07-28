package com.bykeeasy.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "favorite_places")
@Data
public class FavoritePlaceEntity {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String iconType;
}
