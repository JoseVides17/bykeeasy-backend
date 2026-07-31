package com.bykeeasy.infrastructure.adapter.out.persistence.entity;

import com.bykeeasy.domain.model.DriverStatus;
import com.bykeeasy.domain.model.DriverVerificationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "drivers")
@Getter
@Setter
public class DriverEntity {
    @Id
    private String userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String licenseImageUrl;
    private String soatImageUrl;
    private String propertyCardImageUrl;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    @Enumerated(EnumType.STRING)
    private DriverVerificationStatus verificationStatus;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<VehicleEntity> vehicles;

    private Double currentLatitude;
    private Double currentLongitude;
}
