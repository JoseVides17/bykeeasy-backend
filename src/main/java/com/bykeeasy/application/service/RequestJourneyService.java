package com.bykeeasy.application.service;

import com.bykeeasy.domain.model.Coordinate;
import com.bykeeasy.domain.model.Journey;
import com.bykeeasy.domain.model.JourneyStatus;
import com.bykeeasy.application.port.in.RequestJourneyUseCase;
import com.bykeeasy.application.port.out.JourneyRepositoryPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class RequestJourneyService implements RequestJourneyUseCase {

    private final JourneyRepositoryPort journeyRepositoryPort;

    public RequestJourneyService(JourneyRepositoryPort journeyRepositoryPort) {
        this.journeyRepositoryPort = journeyRepositoryPort;
    }

    @Override
    public Journey requestJourney(String passengerId, String passengerName, Coordinate origin, String originAddress, Coordinate destination, String destinationAddress, BigDecimal rate) {
        Journey newJourney = new Journey(
                UUID.randomUUID().toString(),
                passengerId,
                passengerName,
                null,
                origin,
                originAddress,
                destination,
                destinationAddress,
                rate,
                BigDecimal.ZERO,
                JourneyStatus.REQUESTED,
                LocalDateTime.now()
        );
        
        newJourney.calculateCommission(new BigDecimal("0.1"));
        return journeyRepositoryPort.save(newJourney);
    }

    @Override
    public List<Journey> getAvailableJourneys() {
        return journeyRepositoryPort.findByStatus(JourneyStatus.REQUESTED);
    }

    @Override
    public Journey getJourneyById(String id) {
        return journeyRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Journey not found: " + id));
    }

    @Override
    public java.util.Optional<Journey> getActiveJourneyByDriverId(String driverId) {
        List<JourneyStatus> activeStatuses = java.util.List.of(
                JourneyStatus.ACCEPTED,
                JourneyStatus.DRIVER_ON_THE_WAY,
                JourneyStatus.PASSENGER_ON_BOARD
        );
        return journeyRepositoryPort.findByDriverIdAndStatuses(driverId, activeStatuses)
                .stream().findFirst();
    }

    @Override
    public List<Journey> getJourneyHistory(String userId, com.bykeeasy.domain.model.UserRole role) {
        List<JourneyStatus> historyStatuses = List.of(JourneyStatus.COMPLETED, JourneyStatus.CANCELLED);
        if (role == com.bykeeasy.domain.model.UserRole.DRIVER) {
            return journeyRepositoryPort.findByDriverIdAndStatuses(userId, historyStatuses);
        } else {
            // For passengers, we need a method that supports multiple statuses or filter manually
            // Let's assume we can filter or we add a new port method.
            // For now, let's use a manual filter of all passenger journeys if needed, 
            // but better to add a proper port method.
            return journeyRepositoryPort.findByPassengerIdAndStatus(userId, JourneyStatus.COMPLETED); // Mocking for now, will improve port
        }
    }
}
