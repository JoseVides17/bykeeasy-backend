package com.bykeeasy.infrastructure.adapter.out.persistence.entity;

import com.bykeeasy.domain.model.DriverStatus;
import com.bykeeasy.domain.model.DriverVerificationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "drivers")
@Getter
@Setter
public class DriverEntity implements Persistable<String> {
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

    @Transient
    private boolean isNew = true;

    @Override
    public String getId() {
        return this.userId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PostPersist
    void markNotNew() {
        this.isNew = false;
    }
}
