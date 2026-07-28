package com.bykeeasy.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vehicles")
@Data
public class VehicleEntity {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private DriverEntity driver;

    private String licensePlate;
    private String vehicleType;
    private String vehicleModel;
    private String vehicleColor;
    private String vehicleBrand;
    private String imageUrl;
}
