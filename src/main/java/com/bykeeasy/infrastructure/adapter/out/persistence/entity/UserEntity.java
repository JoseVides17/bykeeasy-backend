package com.bykeeasy.infrastructure.adapter.out.persistence.entity;

import com.bykeeasy.domain.model.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String fullName;
    private String phone;
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    private boolean active = true;
    private double rating = 4.5;
    private int numberOfReviews = 0;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private WalletEntity wallet;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PassengerEntity passenger;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private DriverEntity driver;
}
