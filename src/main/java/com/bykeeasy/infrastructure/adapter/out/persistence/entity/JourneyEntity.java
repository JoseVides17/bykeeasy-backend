package com.bykeeasy.infrastructure.adapter.out.persistence.entity;

import com.bykeeasy.domain.model.JourneyStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "journeys")
@Data
public class JourneyEntity implements Persistable<String> {

    @Id
    private String id;

    @Column(nullable = false)
    private String passengerId;

    private String passengerName;

    private String driverId;

    private String driverName;

    private String vehiclePlate;

    private String vehicleModel;

    @Column(nullable = false)
    private Double latOrigin;

    @Column(nullable = false)
    private Double lonOrigin;

    private String originAddress;

    @Column(nullable = false)
    private Double latDestination;

    @Column(nullable = false)
    private Double lonDestination;

    private String destinationAddress;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal fare;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal commission;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JourneyStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Transient
    private boolean isNew = true;

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
}
