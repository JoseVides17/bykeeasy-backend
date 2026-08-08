package com.bykeeasy.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Journey {

    private final String id;
    private final String passengerId;
    private final String passengerName;
    private String driverId;
    private String driverName;
    private String vehiclePlate;
    private String vehicleModel;
    private final Coordinate origin;
    private final String originAddress;
    private final Coordinate destination;
    private final String destinationAddress;
    private BigDecimal fare;
    private BigDecimal commission;
    private JourneyStatus status;
    private final LocalDateTime createdAt;

            BigDecimal fare,
            BigDecimal commission,
            JourneyStatus status,
            LocalDateTime createdAt,
            String driverName,
            String vehiclePlate,
            String vehicleModel
    ) {
        if (passengerId == null || passengerId.isBlank()) {
            throw new IllegalArgumentException("Passenger id is required.");
        }

        if (origin == null) {
            throw new IllegalArgumentException("Origin coordinate is required.");
        }

        if (destination == null) {
            throw new IllegalArgumentException("Destination coordinate is required.");
        }

        if (fare == null || fare.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Fare must be greater than zero.");
        }

        this.id = id;
        this.passengerId = passengerId;
        this.passengerName = passengerName;
        this.driverId = driverId;
        this.driverName = driverName;
        this.vehiclePlate = vehiclePlate;
        this.vehicleModel = vehicleModel;
        this.origin = origin;
        this.originAddress = originAddress;
        this.destination = destination;
        this.destinationAddress = destinationAddress;
        this.fare = fare;
        this.commission = commission == null ? BigDecimal.ZERO : commission;
        this.status = status == null ? JourneyStatus.REQUESTED : status;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public void calculateCommission(BigDecimal commissionRate) {
        if (commissionRate == null || commissionRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Commission rate cannot be negative.");
        }

        this.commission = this.fare.multiply(commissionRate);
    }

    public void assignDriver(String driverId) {
        if (driverId == null || driverId.isBlank()) {
            throw new IllegalArgumentException("Driver id is required.");
        }

        if (this.status != JourneyStatus.REQUESTED) {
            throw new IllegalStateException("Journey is not in REQUESTED status.");
        }

        this.driverId = driverId;
        this.status = JourneyStatus.ACCEPTED;
    }

    public void startJourney() {
        if (this.status != JourneyStatus.ACCEPTED) {
            throw new IllegalStateException("Journey must be ACCEPTED before starting.");
        }

        this.status = JourneyStatus.PASSENGER_ON_BOARD;
    }

    public void completeJourney() {
        if (this.status != JourneyStatus.PASSENGER_ON_BOARD) {
            throw new IllegalStateException("Journey must be in PASSENGER_ON_BOARD status before completing.");
        }

        this.status = JourneyStatus.COMPLETED;
    }

    public void cancel() {
        if (this.status == JourneyStatus.COMPLETED) {
            throw new IllegalStateException("Completed journey cannot be cancelled.");
        }

        this.status = JourneyStatus.CANCELLED;
    }
}
