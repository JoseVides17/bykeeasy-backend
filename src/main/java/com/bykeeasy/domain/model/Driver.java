package com.bykeeasy.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Driver {
    private final String id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private int qualification;
    private DriverStatus status;
    private java.util.List<Vehicle> vehicles;
    private Coordinate currentLocation;
    private DriverVerificationStatus verificationStatus;
    private String profileImageUrl;
    private String licenseImageUrl;
    private String soatImageUrl;
    private String propertyCardImageUrl;

    public Driver(String id, String name, String phone, String email, String password, int qualification, DriverStatus status, java.util.List<Vehicle> vehicles, Coordinate currentLocation) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.qualification = qualification;
        this.status = status;
        this.vehicles = vehicles != null ? vehicles : new java.util.ArrayList<>();
        this.currentLocation = currentLocation;
        this.verificationStatus = DriverVerificationStatus.PENDING;
    }
    
}
